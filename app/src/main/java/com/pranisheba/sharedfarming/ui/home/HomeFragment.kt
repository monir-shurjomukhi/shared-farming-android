package com.pranisheba.sharedfarming.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranisheba.sharedfarming.databinding.FragmentHomeBinding
import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.util.FUND_OPPORTUNITY


class HomeFragment : Fragment() {

  private lateinit var homeViewModel: HomeViewModel
  private var _binding: FragmentHomeBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private var fundOpportunities: List<FundOpportunity>? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    homeViewModel =
      ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    homeViewModel.fundOpportunities.observe(viewLifecycleOwner, Observer {
      //setting recycler to horizontal scroll
      binding.recyclerView.layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

      it.let {
        fundOpportunities = it

        //setting adapter to recycler
        binding.recyclerView.adapter = HorizontalAdapter(it, ::onFundItemClick)
      }
    })

    homeViewModel.getFundOpportunities()

    return root
  }

  private fun onFundItemClick(position: Int) {
    //Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
    val intent = Intent(context, FundDetailsActivity::class.java)
    intent.putExtra(FUND_OPPORTUNITY, fundOpportunities?.get(position))
    startActivity(intent)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}