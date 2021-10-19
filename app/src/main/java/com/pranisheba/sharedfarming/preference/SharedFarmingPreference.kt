package com.pranisheba.sharedfarming.preference

import android.content.Context
import android.content.SharedPreferences
import com.pranisheba.sharedfarming.util.AUTH_TOKEN


const val PREFERENCE_TITLE = "SharedFarmingPreference"

class SharedFarmingPreference(context: Context) {
  private val preferences: SharedPreferences =
    context.getSharedPreferences(PREFERENCE_TITLE, Context.MODE_PRIVATE)
  private val editor: SharedPreferences.Editor = preferences.edit()

  fun putAuthToken(token: String?) {
    editor.putString(AUTH_TOKEN, token)
    editor.apply()
  }

  fun getAuthToken(): String? {
    return preferences.getString(AUTH_TOKEN, "")
  }
}
