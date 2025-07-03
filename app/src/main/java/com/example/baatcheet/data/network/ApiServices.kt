package com.example.baatcheet.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("/send-otp")
    suspend fun sendOtp(@Body request: SendRequest): Response<AuthResponse>

    @POST("/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyRequest): Response<AuthResponse>

}