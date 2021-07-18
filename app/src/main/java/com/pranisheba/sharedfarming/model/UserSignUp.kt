package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserSignUp(
  var phone: String?,
  var email: String?,
  var password: String?,
  var is_investor: Boolean?,
  var is_agreed: Boolean?
) : Parcelable {
  constructor(
    phone: String?,
    password: String?,
    is_investor: Boolean?, is_agreed: Boolean?
  ) : this(phone, null, password, is_investor, is_agreed)
}