package com.example.baatcheet.data.apiservices

import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val ACCOUNT_SID = "ACe2e2aa7de584af988119f0ab7d06dcff" // Your Account SID
    private const val AUTH_TOKEN = "5d6ab1626d4cd8999dc643c0ec254adf" // Twilio Console se
    private const val BASE_URL = "https://api.twilio.com/2010-04-01/Accounts/$ACCOUNT_SID/"

    val twilioApiService: TwilioApiService by lazy {
        val credentials = "$ACCOUNT_SID:$AUTH_TOKEN"
        val base64Credentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Basic $base64Credentials")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TwilioApiService::class.java)
    }
}