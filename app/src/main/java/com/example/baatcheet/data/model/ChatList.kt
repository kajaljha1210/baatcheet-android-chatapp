package com.example.baatcheet.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val imageUrl: String? = "",
    val createdAt: String = ""
)
data class UserPresence(
    val isOnline: Boolean = false,
    val lastSeen: String = ""
)

data class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: String  = "",
    val seen: Boolean = false
)

data class ChatList(
    val chatId: String,
    val partnerId: String,
    val partnerName: String,
    val partnerImage: String?,
    val lastMessage: String,
    val lastMessageTimestamp: String,
    val unreadCount: Int,
    val seen: Boolean
)

