package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Breed(
  var id: Int?,
  var name: String?,
  var bn_name: String?,
  var description: String?
) : Parcelable