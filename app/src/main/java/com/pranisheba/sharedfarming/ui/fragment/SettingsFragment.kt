package com.pranisheba.sharedfarming.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pranisheba.sharedfarming.R
import com.pranisheba.sharedfarming.databinding.FragmentSettingsBinding
import com.pranisheba.sharedfarming.preference.SharedFarmingPreference
import com.pranisheba.sharedfarming.ui.viewmodel.SettingsViewModel
import com.pranisheba.sharedfarming.ui.activity.LoginActivity
import com.pranisheba.sharedfarming.ui.activity.MainActivity
import com.pranisheba.sharedfarming.ui.activity.ProfileActivity

class SettingsFragment : Fragment() {

  private lateinit var settingsViewModel: SettingsViewModel
  private var _binding: FragmentSettingsBinding? = null

  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  private lateinit var preference: SharedFarmingPreference

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    settingsViewModel =
      ViewModelProvider(this).get(SettingsViewModel::class.java)
    _binding = FragmentSettingsBinding.inflate(inflater, container, false)
    val root: View = binding.root
    preference = SharedFarmingPreference(requireContext())

    if (preference.getAuthToken()?.isEmpty() == true) {
      binding.profileCard.visibility = View.GONE
      binding.logInOutTextView.text = getText(R.string.log_in)
      binding.logInOutCard.setOnClickListener {
        startActivity(Intent(context, LoginActivity::class.java))
      }
    } else {
      binding.profileCard.visibility = View.VISIBLE
      binding.logInOutTextView.text = getText(R.string.log_out)
      binding.logInOutCard.setOnClickListener {
        preference.putAuthToken(null)
        startActivity(Intent(context, MainActivity::class.java))
        activity?.finishAffinity()
      }
    }

    binding.profileCard.setOnClickListener {
      startActivity(Intent(context, ProfileActivity::class.java))
    }

    return root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}