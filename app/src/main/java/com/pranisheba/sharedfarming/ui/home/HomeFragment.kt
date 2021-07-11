package com.pranisheba.sharedfarming.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pranisheba.sharedfarming.databinding.FragmentHomeBinding
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.networking.ApiClient
import com.pranisheba.sharedfarming.networking.ApiInterface
import retrofit2.Call
import retrofit2.Response


class HomeFragment : Fragment() {

  private lateinit var homeViewModel: HomeViewModel
  private var _binding: FragmentHomeBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    homeViewModel =
      ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textHome
    homeViewModel.text.observe(viewLifecycleOwner, Observer {
      textView.text = it
    })

    val apiClient = ApiClient().getApiClient()?.create<ApiInterface>(ApiInterface::class.java)
    apiClient?.getFundOpportunities()?.enqueue(object : retrofit2.Callback<List<FundOpportunity>> {
      override fun onResponse(
        call: Call<List<FundOpportunity>>,
        response: Response<List<FundOpportunity>>
      ) {
        homeViewModel.setText(response.body().toString())
      }

      override fun onFailure(call: Call<List<FundOpportunity>>, t: Throwable) {

      }
    })

    return root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}