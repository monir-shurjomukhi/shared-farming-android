package com.pranisheba.sharedfarming.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PaymentCheckout(
  var txID: String?,
  var bankTxID: String?,
  var bankTxStatus: String?,
  var txnAmount: String?,
  var spCode: String?,
  var spCodeDes: String?,
  var paymentOption: String?,
  var farm: String?,
  var unit: String?
) : Parcelable