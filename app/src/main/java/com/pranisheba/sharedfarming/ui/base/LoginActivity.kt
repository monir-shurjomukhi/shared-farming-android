package com.pranisheba.sharedfarming.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivityLoginBinding
import com.pranisheba.sharedfarming.model.UserLogin
import com.pranisheba.sharedfarming.preference.SharedFarmingPreference

class LoginActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoginBinding
  private lateinit var loginViewModel: LoginViewModel
  private lateinit var preference: SharedFarmingPreference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    preference = SharedFarmingPreference(this)

    binding.loginButton.setOnClickListener {
      login()
    }

    binding.signUpTextView.setOnClickListener {
      goToSignUp()
    }

    loginViewModel.userLogin.observe(this, {
      if (it != null) {
        preference.putAuthToken(it.token.toString())
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
      }
    })

    loginViewModel.progress.observe(this, {
      if (it) {
        binding.animationView.visibility = View.VISIBLE
      } else {
        binding.animationView.visibility = View.GONE
      }
    })
  }

  private fun login() {
    val username = binding.usernameLayout.editText?.text.toString()
    val password = binding.passwordLayout.editText?.text.toString()

    if (username.isEmpty()) {
      binding.usernameLayout.error = getString(R.string.phone_number_or_email_required)
      return
    }
    if (password.isEmpty()) {
      binding.passwordLayout.error = getString(R.string.password_required)
      return
    }

    val userLogin = UserLogin(username, password)
    loginViewModel.login(userLogin)
  }

  private fun goToSignUp() {
    startActivity(Intent(this, SignUpActivity::class.java))
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val TAG = "LoginActivity"
  }
}