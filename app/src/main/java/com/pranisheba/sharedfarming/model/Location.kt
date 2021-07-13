package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Location(
  var id: Int?,
  var division: Division?,
  var name: String?,
  var bn_name: String?
) : Parcelable