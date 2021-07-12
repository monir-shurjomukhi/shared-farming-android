package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Breed(
  val id: Int?,
  val name: String?,
  val bn_name: String?,
  val description: String?
) : Parcelable