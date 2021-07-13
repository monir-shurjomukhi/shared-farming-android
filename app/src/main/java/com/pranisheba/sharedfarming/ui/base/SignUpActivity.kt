package com.pranisheba.sharedfarming.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.ActivitySignUpBinding
import com.pranisheba.sharedfarming.model.UserSignUp
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import kotlinx.android.synthetic.main.activity_sign_up.view.*
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySignUpBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySignUpBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
  }

  fun signUp(view: View) {
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
    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.userSignUp(userSignUp)?.enqueue(object : retrofit2.Callback<UserSignUp> {
      override fun onResponse(call: Call<UserSignUp>, response: Response<UserSignUp>) {
        Toast.makeText(this@SignUpActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
      }

      override fun onFailure(call: Call<UserSignUp>, t: Throwable) {

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
}
