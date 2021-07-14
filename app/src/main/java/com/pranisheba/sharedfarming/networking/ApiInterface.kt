package com.pranisheba.sharedfarming.networking

import com.pranisheba.sharedfarming.model.FundOpportunity
import com.pranisheba.sharedfarming.model.PaymentCheckout
import com.pranisheba.sharedfarming.model.UserLogin
import com.pranisheba.sharedfarming.model.UserSignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

  //////////////////////  GET  /////////////////////////

  @GET("sharedfarm/fund_opportunities/")
  fun getFundOpportunities(): Call<List<FundOpportunity>>

  //////////////////////  POST  /////////////////////////

  @POST("auth/signup/")
  fun userSignUp(
    @Body userSignUp: UserSignUp
  ): Call<UserSignUp>

  @POST("auth/api-token-auth/")
  fun userLogin(
    @Body userLogin: UserLogin
  ): Call<UserLogin>

  @POST("sharedfarm/sdk_checkout/")
  fun checkout(
    @Header("Authorization") token: String,
    @Body paymentCheckout: PaymentCheckout
  ): Call<PaymentCheckout>
}
