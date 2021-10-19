package com.pranisheba.sharedfarming.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import retrofit2.Call
import retrofit2.Response

class HomeViewModel : ViewModel() {

  private val _progress = MutableLiveData<Boolean>()
  val progress: LiveData<Boolean>
    get() = _progress

  private val _fundOpportunities = MutableLiveData<List<FundOpportunity>>()
  val fundOpportunities: LiveData<List<FundOpportunity>>
    get() = _fundOpportunities

  fun getFundOpportunities() {
    _progress.value = true
    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.getFundOpportunities()?.enqueue(object : retrofit2.Callback<List<FundOpportunity>> {
      override fun onResponse(
        call: Call<List<FundOpportunity>>,
        response: Response<List<FundOpportunity>>
      ) {
        if (response.isSuccessful) {
          _fundOpportunities.value = response.body()
        }
        _progress.value = false
      }

      override fun onFailure(call: Call<List<FundOpportunity>>, t: Throwable) {
        Log.e(HomeFragment.TAG, "onFailure: ${t.message}", t)
        _progress.value = false
      }
    })
  }
}
