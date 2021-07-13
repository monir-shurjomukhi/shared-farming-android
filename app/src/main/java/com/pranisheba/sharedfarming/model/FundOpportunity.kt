package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FundOpportunity(
  var id: Int?,
  var location: Location?,
  var breed: Breed?,
  var created: String?,
  var modified: String?,
  var category: String?,
  var image: String?,
  var name: String?,
  var amount: Double?,
  var duration: Int?,
  var profit_percentage: Double?,
  var shariah_profit_from: String?,
  var shariah_profit_to: String?,
  var gender: String?,
  var average_weight: Double?,
  var source: String?,
  var growth_timeline: String?,
  var details: String?,
  var is_active: Boolean?,
  var number_of_cows: Int?,
  var faq: Int?
) : Parcelable