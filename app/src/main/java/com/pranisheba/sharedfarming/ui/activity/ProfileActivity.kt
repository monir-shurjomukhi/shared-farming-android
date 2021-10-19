package com.pranisheba.sharedfarming.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

  private lateinit var binding: ActivityProfileBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    //Loading Image into view
    Picasso.get()
      .load("https://expertphotography.b-cdn.net/wp-content/uploads/2020/08/social-media-profile-photos-3.jpg")
      .placeholder(R.drawable.ic_baseline_image_24)
      .error(R.drawable.ic_baseline_broken_image_24)
      .into(binding.profileImage)
  }
}
