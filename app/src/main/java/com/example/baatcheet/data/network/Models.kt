package com.example.baatcheet.data.network


data class SendRequest (
    val phone : String,
)

data class VerifyRequest (
    val phone : String,
    val otp: String? = null ,
)

data class AuthResponse (
    val success: Boolean,
    val message: String,
    val isVerified: Boolean? = null
)
sealed class FirebaseResult<out T> {
    data class Success<out T>(val data: T) : FirebaseResult<T>()
    data class Failure(val message: String) : FirebaseResult<Nothing>()
}


data class Country(
    val emoji: String,
    val dialCode: String,
    val code: String
)
