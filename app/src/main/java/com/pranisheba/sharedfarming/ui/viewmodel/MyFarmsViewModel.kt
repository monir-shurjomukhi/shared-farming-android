package com.pranisheba.sharedfarming.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pranisheba.sharedfarming.model.Invoice
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import com.pranisheba.sharedfarming.ui.fragment.MyFarmsFragment
import retrofit2.Call
import retrofit2.Response

class MyFarmsViewModel : ViewModel() {

  private val _progress = MutableLiveData<Boolean>()
  val progress: LiveData<Boolean>
    get() = _progress

  private val _invoices = MutableLiveData<List<Invoice>>()
  val invoices: LiveData<List<Invoice>>
    get() = _invoices

  fun getInvoices(token: String) {
    _progress.value = true
    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.getInvoices(token)?.enqueue(object : retrofit2.Callback<List<Invoice>> {
      override fun onResponse(
        call: Call<List<Invoice>>,
        response: Response<List<Invoice>>
      ) {
        if (response.isSuccessful) {
          _invoices.value = response.body()
        }
        _progress.value = false
      }

      override fun onFailure(call: Call<List<Invoice>>, t: Throwable) {
        Log.e(MyFarmsFragment.TAG, "onFailure: ${t.message}", t)
        _progress.value = false
      }
    })
  }
}