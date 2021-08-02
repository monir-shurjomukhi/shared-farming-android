package com.pranisheba.sharedfarming.ui.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pranisheba.sharedfarming.model.UserLogin
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import retrofit2.Call
import retrofit2.Response

class LoginViewModel : ViewModel() {

  private val _progress = MutableLiveData<Boolean>()
  val progress: LiveData<Boolean>
    get() = _progress

  private val _userLogin = MutableLiveData<UserLogin>()
  val userLogin: LiveData<UserLogin>
    get() = _userLogin

  fun login(userLogin: UserLogin) {
    _progress.value = true
    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.userLogin(userLogin)?.enqueue(object : retrofit2.Callback<UserLogin> {
      override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>) {
        if (response.isSuccessful) {
          _userLogin.value = response.body()
        }

        _progress.value = false
      }

      override fun onFailure(call: Call<UserLogin>, t: Throwable) {
        _progress.value = false
        Log.e(LoginActivity.TAG, "onFailure: ${t.message}", t)
      }
    })
  }
}
