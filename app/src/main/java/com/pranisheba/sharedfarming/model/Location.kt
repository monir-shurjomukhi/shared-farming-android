package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Location(
  val id: Int?,
  val division: Division?,
  val name: String?,
  val bn_name: String?
) : Parcelable