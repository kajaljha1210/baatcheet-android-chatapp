package com.example.baatcheet.ui.utils

object Validator{

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
      return phoneNumber.length <= 10 && phoneNumber.isNotEmpty() && phoneNumber.all { it.isDigit() }
    }
    fun isOTPValid(otp: String): Boolean {
        return otp.length == 6 && otp.all { it.isDigit() }
    }
    fun isNameValid(name: String): Boolean {
        return name.trim().isNotEmpty()
    }

}