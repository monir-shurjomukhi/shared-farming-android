package com.pranisheba.sharedfarming.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivitySignUpBinding
import com.pranisheba.sharedfarming.model.UserSignUp

class SignUpActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySignUpBinding
  private lateinit var signUpViewModel: SignUpViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySignUpBinding.inflate(layoutInflater)
    signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    binding.signUpButton.setOnClickListener {
      signUp()
    }

    binding.loginTextView.setOnClickListener {
      goToLogin()
    }

    signUpViewModel.userSignUp.observe(this, {
      if (it != null) {
        Toast.makeText(this, "Signup successful. Please Login.", Toast.LENGTH_SHORT).show()
      }
    })

    signUpViewModel.progress.observe(this, {
      if (it) {
        binding.animationView.visibility = View.VISIBLE
      } else {
        binding.animationView.visibility = View.GONE
      }
    })
  }

  private fun signUp() {
    val phone = binding.phoneLayout.editText?.text.toString()
    val email = binding.emailLayout.editText?.text.toString()
    val password = binding.passwordLayout.editText?.text.toString()
    val retypePassword = binding.retypePasswordLayout.editText?.text.toString()

    if (phone.isEmpty()) {
      binding.phoneLayout.error = getString(R.string.phone_number_required)
      return
    }
    if (password.isEmpty()) {
      binding.passwordLayout.error = getString(R.string.password_required)
      return
    }
    if (retypePassword.isEmpty()) {
      binding.retypePasswordLayout.error = getString(R.string.retype_password_required)
      return
    }
    if (!binding.termsCheckBox.isChecked) {
      Toast.makeText(this, getString(R.string.accept_terms_and_conditions), Toast.LENGTH_SHORT)
        .show()
      return
    }
    if (password != retypePassword) {
      Toast.makeText(this, getString(R.string.password_did_not_match), Toast.LENGTH_SHORT)
        .show()
      return
    }

    val userSignUp = UserSignUp(phone, password, is_investor = true, is_agreed = true)
    if (email.isNotEmpty()) userSignUp.email = email
    signUpViewModel.signUp(userSignUp)
  }

  private fun goToLogin() {
    onBackPressed()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val TAG = "SignUpActivity"
  }
}
