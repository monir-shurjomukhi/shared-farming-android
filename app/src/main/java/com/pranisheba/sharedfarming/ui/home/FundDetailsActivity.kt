package com.pranisheba.sharedfarming.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivityFundDetailsBinding
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.ui.base.LoginActivity
import com.pranisheba.sharedfarming.util.FUND_OPPORTUNITY
import com.squareup.picasso.Picasso

class FundDetailsActivity : AppCompatActivity() {

  private lateinit var binding: ActivityFundDetailsBinding
  private lateinit var fundOpportunity: FundOpportunity

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFundDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    fundOpportunity = intent.getParcelableExtra(FUND_OPPORTUNITY)!!

    Picasso.get().load(fundOpportunity.image).placeholder(R.mipmap.ic_launcher)
      .into(binding.fundImageView)
    binding.fundNameTextView.text = fundOpportunity.name
    binding.fundAmountTextView.text = String.format("%.2f/cow", fundOpportunity.amount)
    binding.fundDetailsTextView.text = fundOpportunity.details
  }

  fun buyNow(view: View) {
    startActivity(Intent(this, LoginActivity::class.java))
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}