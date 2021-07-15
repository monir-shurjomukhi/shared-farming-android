package com.pranisheba.sharedfarming.ui.myfarms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pranisheba.sharedfarming.model.Invoice
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import retrofit2.Call
import retrofit2.Response

class MyFarmsViewModel : ViewModel() {

  private val _invoices = MutableLiveData<List<Invoice>>()
  val invoices: LiveData<List<Invoice>>
    get() = _invoices

  fun getInvoices(token: String) {
    val apiClient = ApiClient().getApiClient()?.create(ApiInterface::class.java)
    apiClient?.getInvoices(token)?.enqueue(object : retrofit2.Callback<List<Invoice>> {
      override fun onResponse(
        call: Call<List<Invoice>>,
        response: Response<List<Invoice>>
      ) {
        _invoices.value = response.body()
      }

      override fun onFailure(call: Call<List<Invoice>>, t: Throwable) {

      }
    })
  }
}