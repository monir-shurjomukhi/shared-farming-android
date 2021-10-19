package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Invoice(
  var id: Int?,
  var product: FundOpportunity?,
  var timestamp: String?,
  var amount: String?,
  var unit: Int?,
  var is_paid: Boolean?,
  var user: Int?,
  var payment: String?
) : Parcelable
