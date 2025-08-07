package com.example.baatcheet.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    fun formatTime(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return format.format(date)
    }



}
