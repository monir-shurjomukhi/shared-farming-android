package com.pranisheba.sharedfarming.preference

import android.content.Context
import android.content.SharedPreferences


const val PREFERENCE_TITLE = "SharedFarmingPreference"

class VetPreference(context: Context) {
  private val preferences: SharedPreferences =
    context.getSharedPreferences(PREFERENCE_TITLE, Context.MODE_PRIVATE)
  private val editor: SharedPreferences.Editor = preferences.edit()

  
}
