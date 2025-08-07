package com.example.baatcheet.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baatcheet.data.model.ChatList
import com.example.baatcheet.data.model.Message
import com.example.baatcheet.data.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _chatId = MutableStateFlow<String?>(null)
    val chatId: StateFlow<String?> = _chatId

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _messageText = MutableStateFlow("")
    val messageText: StateFlow<String> = _messageText

    private val _chatList = MutableStateFlow<List<ChatList>>(emptyList())
    val chatList: StateFlow<List<ChatList>> = _chatList

    fun loadChatList(currentUserId: String) {
        viewModelScope.launch {
            val result = chatRepository.getChatList(currentUserId)
            _chatList.value = result
        }
    }
    fun onMessageTextChange(text: String) {
        _messageText.value = text
    }

    fun initializeChat(senderId: String, receiverId: String) {
        viewModelScope.launch {
            val id = chatRepository.createChatId(senderId, receiverId)
            _chatId.value = id
            observeMessages(id)
            markSeen(id, senderId)
        }
    }

    private fun observeMessages(chatId: String) {
        viewModelScope.launch {
            chatRepository.getMessages(chatId).collect {
                _messages.value = it
            }
        }
    }
    fun sendMessage(senderId: String, receiverId: String) {
        val msg = messageText.value.trim()
        if (msg.isEmpty() || chatId.value == null) return

        viewModelScope.launch {
            chatRepository.sendMessage(senderId, receiverId, msg)
            _messageText.value = ""
        }
    }
    private fun markSeen(chatId: String, userId: String) {
        viewModelScope.launch {
            chatRepository.markMessagesAsSeen(chatId, userId)
        }
    }


}
