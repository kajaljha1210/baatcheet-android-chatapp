package com.example.baatcheet.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val imageUrl: String? = "",
    val isOnline: Boolean = false,
    val createdAt: String? = ""
)
