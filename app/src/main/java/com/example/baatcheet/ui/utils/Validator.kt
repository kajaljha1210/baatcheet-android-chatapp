package com.example.baatcheet.ui.utils

object Validator {

    private const val MAX_PHONE_LENGTH = 10
    private const val OTP_LENGTH = 6

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length <= MAX_PHONE_LENGTH &&
                phoneNumber.isNotEmpty() &&
                phoneNumber.all { it.isDigit() }
    }

    fun isOTPValid(otp: String): Boolean {
        return otp.length == OTP_LENGTH && otp.all { it.isDigit() }
    }

    fun isNameValid(name: String): Boolean {
        return name.trim().isNotEmpty()
    }
}
