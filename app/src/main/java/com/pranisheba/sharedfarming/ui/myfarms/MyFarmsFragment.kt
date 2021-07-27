package com.pranisheba.sharedfarming.ui.myfarms

import android.content.Intent
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
import com.pranisheba.sharedfarming.ui.base.LoginActivity

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

    if (preference.getAuthToken()?.isEmpty() == true) {
      Toast.makeText(context, "Login to view your funds", Toast.LENGTH_SHORT).show()
      startActivity(Intent(context, LoginActivity::class.java))
    } else {
      myFarmsViewModel.invoices.observe(viewLifecycleOwner, Observer {
        binding.recyclerView.layoutManager =
          LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        if (it != null) {
          if (it.isNotEmpty()) {
            invoices = it
            //setting adapter to recycler
            binding.recyclerView.adapter = FarmAdapter(it, ::onFarmItemClick)
          } else {
            Toast.makeText(context, "No Farms Found!", Toast.LENGTH_SHORT).show()
          }
        }
      })

      myFarmsViewModel.progress.observe(viewLifecycleOwner, {
        if (it) {
          binding.animationView.visibility = View.VISIBLE
        } else {
          binding.animationView.visibility = View.GONE
        }
      })
    }

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

  companion object {
    const val TAG = "MyFarmsFragment"
  }
}