package com.example.baatcheet.data.model

data class ChatList (
    val id: Int,
    val name: String,
    val imageRes : Int,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: String,
)