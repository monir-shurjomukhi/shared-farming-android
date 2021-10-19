package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserLogin(
  var username: String?,
  var password: String?,
  var token: String?
) : Parcelable {
  constructor(
    username: String?,
    password: String?
  ) : this(username, password, null)
}