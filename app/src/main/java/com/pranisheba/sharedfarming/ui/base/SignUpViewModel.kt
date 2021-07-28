package com.pranisheba.sharedfarming.ui.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pranisheba.sharedfarming.model.UserSignUp
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import retrofit2.Call
import retrofit2.Response

class SignUpViewModel : ViewModel() {

  private val _progress = MutableLiveData<Boolean>()
  val progress: LiveData<Boolean>
    get() = _progress

  private val _userSignUp = MutableLiveData<UserSignUp>()
  val userSignUp: LiveData<UserSignUp>
    get() = _userSignUp

  fun signUp(userSignUp: UserSignUp) {
    _progress.value = true
    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.userSignUp(userSignUp)?.enqueue(object : retrofit2.Callback<UserSignUp> {
      override fun onResponse(call: Call<UserSignUp>, response: Response<UserSignUp>) {
        if (response.isSuccessful) {
          _userSignUp.value = response.body()
        }

        _progress.value = false
      }

      override fun onFailure(call: Call<UserSignUp>, t: Throwable) {
        Log.e(SignUpActivity.TAG, "onFailure: ${t.message}", t)
        _progress.value = false
      }
    })
  }
}
