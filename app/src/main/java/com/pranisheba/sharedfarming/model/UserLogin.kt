package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserLogin(
  var username: String?,
  var password: String?,
  var token: String?
) : Parcelable