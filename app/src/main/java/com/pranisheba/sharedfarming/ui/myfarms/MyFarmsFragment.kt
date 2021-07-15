package com.pranisheba.sharedfarming.ui.myfarms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pranisheba.sharedfarming.databinding.FragmentMyFarmsBinding
import com.pranisheba.sharedfarming.model.Invoice
import com.pranisheba.sharedfarming.preference.SharedFarmingPreference

class MyFarmsFragment : Fragment() {

  private lateinit var myFarmsViewModel: MyFarmsViewModel
  private var _binding: FragmentMyFarmsBinding? = null
  private lateinit var preference: SharedFarmingPreference

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private var invoices: List<Invoice>? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    myFarmsViewModel =
      ViewModelProvider(this).get(MyFarmsViewModel::class.java)

    _binding = FragmentMyFarmsBinding.inflate(inflater, container, false)
    val root: View = binding.root

    preference = SharedFarmingPreference(requireContext())

    myFarmsViewModel.invoices.observe(viewLifecycleOwner, Observer {
      binding.recyclerView.layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

      it.let {
        if (it.isNotEmpty()) {
          invoices = it
          //setting adapter to recycler
          binding.recyclerView.adapter = FarmAdapter(it, ::onFarmItemClick)
        } else {
          Toast.makeText(context, "No Farms Found!", Toast.LENGTH_SHORT).show()
        }
      }
    })

    myFarmsViewModel.getInvoices("Token " + preference.getAuthToken())

    return root
  }

  private fun onFarmItemClick(position: Int) {
    Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
    /*val intent = Intent(context, FundDetailsActivity::class.java)
    intent.putExtra(FUND_OPPORTUNITY, fundOpportunities?.get(position))
    startActivity(intent)*/
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}