package com.pranisheba.sharedfarming.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivityLoginBinding
import com.pranisheba.sharedfarming.model.UserLogin
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import com.pranisheba.sharedfarming.preference.SharedFarmingPreference
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoginBinding
  private lateinit var preference: SharedFarmingPreference

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
    preference = SharedFarmingPreference(this)
  }

  fun login(view: View) {
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
    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.userLogin(userLogin)?.enqueue(object : retrofit2.Callback<UserLogin> {
      override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
        Toast.makeText(this@LoginActivity, response.body().toString(), Toast.LENGTH_SHORT)
          .show()
        preference.putAuthToken(response.body()?.token.toString())
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finishAffinity()
      }

      override fun onFailure(call: Call<UserLogin>, t: Throwable) {

      }

    })
  }

  fun goToSignUp(view: View) {
    startActivity(Intent(this, SignUpActivity::class.java))
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}