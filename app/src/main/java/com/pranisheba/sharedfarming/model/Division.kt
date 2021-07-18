package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Division(
  var id: Int?,
  var name: String?,
  var bn_name: String?
) : Parcelable