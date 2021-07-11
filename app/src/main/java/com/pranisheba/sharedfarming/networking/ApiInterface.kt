package com.pranisheba.sharedfarming.networking

import com.pranisheba.sharedfarming.model.FundOpportunity
import retrofit2.Call
import retrofit2.http.GET




interface ApiInterface {

  @GET("/sharedfarm/fund_opportunities/")
  fun getFundOpportunities(): Call<List<FundOpportunity>>
}