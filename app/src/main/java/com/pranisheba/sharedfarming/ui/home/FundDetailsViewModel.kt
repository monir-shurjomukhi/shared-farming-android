package com.pranisheba.sharedfarming.ui.home

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.model.PaymentCheckout
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import com.sm.shurjopaysdk.listener.PaymentResultListener
import com.sm.shurjopaysdk.model.RequiredDataModel
import com.sm.shurjopaysdk.model.TransactionInfo
import com.sm.shurjopaysdk.payment.ShurjoPaySDK
import com.sm.shurjopaysdk.utils.SPayConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FundDetailsViewModel : ViewModel() {

  private val _progress = MutableLiveData<Boolean>()
  val progress: LiveData<Boolean>
    get() = _progress

  private val _paymentCheckout = MutableLiveData<PaymentCheckout>()
  val paymentCheckout: LiveData<PaymentCheckout>
    get() = _paymentCheckout

  fun buy(context: Context, unit: Int, fundOpportunity: FundOpportunity, token: String) {
    val amount = unit * fundOpportunity.amount!!

    val dataModel = RequiredDataModel(
      "spaytest",
      "JehPNXF58rXs",
      "NOK" + System.currentTimeMillis(),
      amount,
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiJzcGF5dGVzdCIsImlhdCI6MTU5ODM2MTI1Nn0.cwkvdTDI6_K430xq7Iqapaknbqjm9J3Th1EiXePIEcY"
    )
    ShurjoPaySDK.getInstance().makePayment(
      context as Activity?,
      SPayConstants.SdkType.TEST,
      dataModel,
      object : PaymentResultListener {
        override fun onSuccess(t: TransactionInfo?) {
          Log.d(FundDetailsActivity.TAG, "onSuccess: $t")
          Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
          if (t != null) {
            confirmPayment(unit, t, fundOpportunity, token)
          }
        }

        override fun onFailed(e: String?) {
          Log.e(FundDetailsActivity.TAG, "onFailed: $e")
          Toast.makeText(context, e, Toast.LENGTH_SHORT).show()
        }
      }
    )
  }

  fun confirmPayment(
    unit: Int,
    tInfo: TransactionInfo,
    fundOpportunity: FundOpportunity,
    token: String
  ) {
    _progress.value = true

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
    apiClient?.checkout(token, paymentCheckout)
      ?.enqueue(object : Callback<PaymentCheckout> {
        override fun onResponse(call: Call<PaymentCheckout>, response: Response<PaymentCheckout>) {
          Log.d(FundDetailsActivity.TAG, "onResponse: ${response.body().toString()}")

          if (response.isSuccessful) {
            _paymentCheckout.value = response.body()
          }
          _progress.value = false
        }

        override fun onFailure(call: Call<PaymentCheckout>, t: Throwable) {
          Log.e(FundDetailsActivity.TAG, "onFailure: $t", t)
          _progress.value = false
        }
      })
  }
}