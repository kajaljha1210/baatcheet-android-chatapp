package com.example.baatcheet.data.repository

import android.util.Log
import com.example.baatcheet.data.network.ApiServices
import com.example.baatcheet.data.network.AuthResponse
import com.example.baatcheet.data.network.SendRequest
import com.example.baatcheet.data.network.VerifyRequest
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
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

        return safeApiCall {
            apiServices.sendOtp(request)
        }
    }

    override suspend fun verifyOtp(phone: String, otp: String): AuthResponse {
        val request = VerifyRequest(phone, otp)
        Log.d("AuthRepo", "verifyOtp() - Request: ${Gson().toJson(request)}")

        return safeApiCall {
            apiServices.verifyOtp(request)
        }
    }

    private suspend fun safeApiCall(call: suspend () -> Response<AuthResponse>): AuthResponse {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body() ?: AuthResponse(false, "Empty response from server")
            } else {
                AuthResponse(false, "Server error: ${response.code()}")
            }
        } catch (e: SocketTimeoutException) {
            Log.e("AuthRepo", "Timeout Error", e)
            AuthResponse(false, "Request timed out. Please try again.")
        } catch (e: IOException) {
            Log.e("AuthRepo", "Network Error", e)
            AuthResponse(false, "No internet connection. Please check and retry.")
        } catch (e: Exception) {
            Log.e("AuthRepo", "Unknown Error", e)
            AuthResponse(false, e.message ?: "Something went wrong.")
        }
    }
}
