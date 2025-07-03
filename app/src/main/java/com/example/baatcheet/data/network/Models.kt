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
sealed class FirebaseResult {
    object Success : FirebaseResult()
    data class Failure(val message: String) : FirebaseResult()
}

data class Country(
    val emoji: String,
    val dialCode: String,
    val code: String
)
