package com.example.baatcheet.ui.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.example.baatcheet.MainActivity

data class Credentials(
    var login: String = "",
    var pwd: String = "",
    var remember: Boolean = false
) {
    fun isNotEmpty(): Boolean {
        return login.isNotEmpty() && pwd.isNotEmpty()
    }
}

