package com.example.baatcheet.data.repository

import  android.util.Log
import com.example.baatcheet.data.network.ApiServices
import com.example.baatcheet.data.network.AuthResponse
import com.example.baatcheet.data.network.SendRequest
import com.example.baatcheet.data.network.VerifyRequest
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

interface AuthRepository {
    suspend fun sendOtp(phone: String): AuthResponse
    suspend fun verifyOtp(phone: String, otp: String): AuthResponse
}
class AuthRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
) : AuthRepository {

    override suspend fun sendOtp(phone: String): AuthResponse {
        val request = SendRequest(phone)
        Log.d("AuthRepo", "sendOtp() - Request: ${Gson().toJson(request)}")
        val response = apiServices.sendOtp(request)
        return handleApiResponse(response)
    }

    override suspend fun verifyOtp(phone: String, otp: String): AuthResponse {
        val request = VerifyRequest(phone, otp)
        Log.d("AuthRepo", "verifyOtp() - Request: ${Gson().toJson(request)}")
        val response = apiServices.verifyOtp(request)
        return handleApiResponse(response)
    }


     fun handleApiResponse(response: Response<AuthResponse>): AuthResponse {
        return try {
            if (response.isSuccessful) {
                response.body() ?: AuthResponse(false, "Empty response from server")
            } else {
                AuthResponse(false, "Server error: ${response.code()}")
            }
        } catch (e: IOException) {
            AuthResponse(false, "Network error")
        } catch (e: Exception) {
            AuthResponse(false, e.message ?: "Unknown error")
        }
    }

}
