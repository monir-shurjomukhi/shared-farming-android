package com.pranisheba.sharedfarming.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivityFundDetailsBinding
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.model.PaymentCheckout
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import com.pranisheba.sharedfarming.preference.SharedFarmingPreference
import com.pranisheba.sharedfarming.ui.base.LoginActivity
import com.pranisheba.sharedfarming.ui.base.MainActivity
import com.pranisheba.sharedfarming.util.FUND_OPPORTUNITY
import com.sm.shurjopaysdk.listener.PaymentResultListener
import com.sm.shurjopaysdk.model.RequiredDataModel
import com.sm.shurjopaysdk.model.TransactionInfo
import com.sm.shurjopaysdk.payment.ShurjoPaySDK
import com.sm.shurjopaysdk.utils.SPayConstants
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FundDetailsActivity : AppCompatActivity() {

  private lateinit var binding: ActivityFundDetailsBinding
  private lateinit var preference: SharedFarmingPreference
  private lateinit var fundOpportunity: FundOpportunity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFundDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    preference = SharedFarmingPreference(this)
    fundOpportunity = intent.getParcelableExtra(FUND_OPPORTUNITY)!!

    Picasso.get().load(fundOpportunity.image).placeholder(R.mipmap.ic_launcher)
      .into(binding.fundImageView)
    binding.fundNameTextView.text = fundOpportunity.name
    binding.fundAmountTextView.text = String.format("%.2f/cow", fundOpportunity.amount)
    binding.breedTextView.text = fundOpportunity.breed?.name
    binding.genderTextView.text = fundOpportunity.gender
    binding.weightTextView.text = String.format("%.2f KG", fundOpportunity.average_weight)
    binding.sourceTextView.text = fundOpportunity.source
    binding.locationTextView.text = fundOpportunity.location?.name
    binding.timelineTextView.text = fundOpportunity.growth_timeline
    binding.fundDetailsTextView.text = fundOpportunity.details

    binding.unitLayout.setOnKeyListener(null)
    ArrayAdapter(this, android.R.layout.simple_list_item_1, listOf("1", "2", "3", "4"))
      .also { adapter ->
        binding.unitTextView.setAdapter(adapter)
        binding.unitTextView.inputType = InputType.TYPE_NULL
      }
  }

  fun buyNow(view: View) {
    val unit: Int
    try {
      unit = binding.unitLayout.editText?.text.toString().toInt()
    } catch (e: Exception) {
      binding.unitLayout.error = getString(R.string.unit_required)
      return
    }
    binding.unitLayout.editText?.text.toString()
    val amount = unit * fundOpportunity.amount!!

    if (preference.getAuthToken()?.isEmpty() == true) {
      startActivity(Intent(this, LoginActivity::class.java))
    } else {
      val dataModel = RequiredDataModel(
        "spaytest",
        "JehPNXF58rXs",
        "NOK" + System.currentTimeMillis(),
        amount,
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJzcGF5dGVzdCIsImlhdCI6MTU5ODM2MTI1Nn0.cwkvdTDI6_K430xq7Iqapaknbqjm9J3Th1EiXePIEcY"
      )
      ShurjoPaySDK.getInstance().makePayment(
        this,
        SPayConstants.SdkType.TEST,
        dataModel,
        object : PaymentResultListener {
          override fun onSuccess(t: TransactionInfo?) {
            Log.d(TAG, "onSuccess: $t")
            Toast.makeText(this@FundDetailsActivity, t.toString(), Toast.LENGTH_SHORT).show()
            if (t != null) {
              confirmPayment(unit, t)
            }
          }

          override fun onFailed(e: String?) {
            Log.e(TAG, "onFailed: $e")
            Toast.makeText(this@FundDetailsActivity, e, Toast.LENGTH_SHORT).show()
          }
        }
      )
    }
  }

  fun confirmPayment(unit: Int, tInfo: TransactionInfo) {
    val paymentCheckout = PaymentCheckout(
      tInfo.txID,
      tInfo.bankTxID,
      tInfo.bankTxStatus,
      tInfo.txnAmount.toString(),
      tInfo.spCode,
      tInfo.spCode,
      "shurjopay",
      fundOpportunity.id.toString(),
      unit.toString()
    )

    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.checkout("Token " + preference.getAuthToken(), paymentCheckout)
      ?.enqueue(object : Callback<PaymentCheckout> {
        override fun onResponse(call: Call<PaymentCheckout>, response: Response<PaymentCheckout>) {
          Log.d(TAG, "onResponse: ${response.body().toString()}")
          Toast.makeText(
            this@FundDetailsActivity,
            "Congratulations! Your Fund is Successful!",
            Toast.LENGTH_SHORT
          ).show()
          startActivity(Intent(this@FundDetailsActivity, MainActivity::class.java))
          finishAffinity()
        }

        override fun onFailure(call: Call<PaymentCheckout>, t: Throwable) {
          Log.e(TAG, "onFailure: $t", t)
        }
      })
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    private const val TAG = "FundDetailsActivity"
  }
}