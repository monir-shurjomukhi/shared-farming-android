package com.pranisheba.sharedfarming.networking

import com.pranisheba.sharedfarming.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {
  private var retrofit: Retrofit? = null

  fun getApiClient(): Retrofit? {
    if (retrofit == null) {
      val interceptor = HttpLoggingInterceptor()
      interceptor.level = HttpLoggingInterceptor.Level.BODY

      val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

      retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    }
    return retrofit
  }
}