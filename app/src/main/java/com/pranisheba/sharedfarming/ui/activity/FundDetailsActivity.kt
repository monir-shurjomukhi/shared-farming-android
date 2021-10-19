package com.pranisheba.sharedfarming.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivityFundDetailsBinding
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.preference.SharedFarmingPreference
import com.pranisheba.sharedfarming.ui.viewmodel.FundDetailsViewModel
import com.pranisheba.sharedfarming.util.FUND_OPPORTUNITY
import com.squareup.picasso.Picasso

class FundDetailsActivity : AppCompatActivity() {

  private lateinit var fundDetailsViewModel: FundDetailsViewModel
  private lateinit var binding: ActivityFundDetailsBinding
  private lateinit var preference: SharedFarmingPreference
  private lateinit var fundOpportunity: FundOpportunity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFundDetailsBinding.inflate(layoutInflater)
    fundDetailsViewModel = ViewModelProvider(this).get(FundDetailsViewModel::class.java)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    preference = SharedFarmingPreference(this)
    fundOpportunity = intent.getParcelableExtra(FUND_OPPORTUNITY)!!

    Picasso.get().load(fundOpportunity.image).placeholder(R.drawable.ic_baseline_image_24)
      .error(R.drawable.ic_baseline_broken_image_24).into(binding.fundImageView)
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

    binding.buyNowButton.setOnClickListener {
      buyNow()
    }

    fundDetailsViewModel.paymentCheckout.observe(this, {
      Toast.makeText(
        this,
        "Congratulations! Your Fund is Successful!",
        Toast.LENGTH_SHORT
      ).show()
      startActivity(Intent(this, MainActivity::class.java))
      finishAffinity()
    })

    fundDetailsViewModel.progress.observe(this, {
      if (it) {
        binding.animationView.visibility = View.VISIBLE
      } else {
        binding.animationView.visibility = View.GONE
      }
    })
  }

  private fun buyNow() {
    val unit: Int
    try {
      unit = binding.unitLayout.editText?.text.toString().toInt()
    } catch (e: Exception) {
      binding.unitLayout.error = getString(R.string.unit_required)
      return
    }

    if (preference.getAuthToken()?.isEmpty() == true) {
      startActivity(Intent(this, LoginActivity::class.java))
    } else {
      val token = "Token " + preference.getAuthToken()
      fundDetailsViewModel.buy(this, unit, fundOpportunity, token)
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val TAG = "FundDetailsActivity"
  }
}