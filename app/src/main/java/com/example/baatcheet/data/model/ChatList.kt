package com.example.baatcheet.data.model
data class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: String  = ""
    , // ✅ default
    val seen: Boolean = false
)

data class ChatListItemData(
    val chatId: String,
    val partnerId: String,
    val partnerName: String, // ✅ new
    val partnerImage: String?, // ✅ optional
    val lastMessage: String,
    val lastMessageTimestamp: String,
    val unreadCount: Int,
    val seen: Boolean
)


data class User(
    val uid: String = "",
    val name: String = "",
    val phone: String = "",
    val imageUrl: String? = "",
    val isOnline: Boolean = false,
    val createdAt: String = ""  // ✅ Long instead of String
)
