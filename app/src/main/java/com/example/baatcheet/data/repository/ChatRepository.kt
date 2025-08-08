package com.example.baatcheet.data.repository

import com.example.baatcheet.data.model.ChatList
import com.example.baatcheet.data.model.Message
import com.example.baatcheet.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

interface ChatRepository {
    suspend fun createChatId(senderId: String, receiverId: String): String
    suspend fun getChatList(currentUserId: String): Flow<List<ChatList>>
    suspend fun sendMessage(senderId: String, receiverId: String, message: String)
    suspend fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun markMessagesAsSeen(chatId: String, userId: String)
}

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChatRepository {

    private suspend fun fetchChatId(senderId: String, receiverId: String): String? {
        val snapshot = firestore.collection("chats")
            .whereEqualTo("participantsKey", setOf(senderId, receiverId).sorted().joinToString("_"))
            .get().await()

        return snapshot.documents.firstOrNull()?.id
    }

    override suspend fun createChatId(senderId: String, receiverId: String): String {
        val existing = fetchChatId(senderId, receiverId)
        if (existing != null) return existing

        val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val formattedTime = formatter.format(Date())

        val newChatId = UUID.randomUUID().toString()
        val chatData = mapOf(
            "participants" to listOf(senderId, receiverId),
            "participantsKey" to setOf(senderId, receiverId).sorted().joinToString("_"),
            "createdAt" to formattedTime
        )
        firestore.collection("chats").document(newChatId).set(chatData).await()
        return newChatId
    }
    override suspend fun getChatList(currentUserId: String): Flow<List<ChatList>> = callbackFlow {
        val usersRef = firestore.collection("users")
        val chatsRef = firestore.collection("chats")
            .whereArrayContains("participants", currentUserId)

        var latestUsers: List<User> = emptyList()
        var latestChatsSnapshot: QuerySnapshot? = null

        fun emitCombinedData() {
            if (latestChatsSnapshot == null || latestUsers.isEmpty()) return

            val chatList = latestUsers.map { user ->
                val chatDoc = latestChatsSnapshot!!.documents.find { doc ->
                    val participants = doc.get("participants") as? List<*>
                    participants?.contains(user.uid) == true
                }

                val data = chatDoc?.data
                ChatList(
                    chatId = chatDoc?.id ?: "",
                    partnerId = user.uid,
                    partnerName = user.name,
                    partnerImage = user.imageUrl,
                    lastMessage = data?.get("lastMessage") as? String ?: "",
                    lastMessageTimestamp = data?.get("lastMessageTimestamp") as? String ?: "",
                    unreadCount = ((data?.get("unreadCount") as? Map<*, *>)?.get(currentUserId) as? Number)?.toInt() ?: 0,
                    seen = ((data?.get("seenStatus") as? Map<*, *>)?.get(currentUserId) as? Boolean) ?: false
                )
            }

            trySend(chatList).isSuccess
        }

        // 👤 Listen to users
        val usersListener = usersRef.addSnapshotListener { userSnap, userError ->
            if (userError != null || userSnap == null) return@addSnapshotListener

            latestUsers = userSnap.documents.mapNotNull { it.toObject(User::class.java) }
                .filter { it.uid != currentUserId }

            emitCombinedData()
        }

        // 💬 Listen to chats
        val chatsListener = chatsRef.addSnapshotListener { chatSnap, chatError ->
            if (chatError != null || chatSnap == null) return@addSnapshotListener

            latestChatsSnapshot = chatSnap
            emitCombinedData()
        }

        awaitClose {
            usersListener.remove()
            chatsListener.remove()
        }
    }

    override suspend fun sendMessage(senderId: String, receiverId: String, message: String) {
        val chatId = createChatId(senderId, receiverId)
        val chatRef = firestore.collection("chats").document(chatId)
        val messageRef = chatRef.collection("messages").document()

        val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val formattedTime = formatter.format(Date())

        val messageData = mapOf(
            "senderId" to senderId,
            "receiverId" to receiverId,
            "text" to message,
            "timestamp" to formattedTime,
            "seen" to false
        )

        val chatMetaData = mapOf(
            "lastMessage" to message,
            "lastMessageTimestamp" to formattedTime,
            "unreadCount" to mapOf(
                receiverId to FieldValue.increment(1),
                senderId to 0
            ),
            "seenStatus" to mapOf(
                receiverId to false,
                senderId to true
            )
        )

        firestore.runBatch { batch ->
            batch.set(messageRef, messageData)
            batch.set(chatRef, chatMetaData, SetOptions.merge())
        }.await()
    }

    override suspend fun getMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        val listener = firestore.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    close(error)
                    return@addSnapshotListener
                }

                val messages = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Message::class.java)
                }
                trySend(messages)
            }

        awaitClose { listener.remove() }
    }
    override suspend fun markMessagesAsSeen(chatId: String, userId: String) {
        val chatRef = firestore.collection("chats").document(chatId)
        val messagesRef = chatRef.collection("messages")

        // Step 1: Update chat metadata (unreadCount, seenStatus)
        firestore.runBatch { batch ->
            batch.set(chatRef, mapOf(
                "unreadCount" to mapOf(userId to 0),
                "seenStatus" to mapOf(userId to true)
            ), SetOptions.merge())
        }.await()

        // Step 2: Get unseen messages
        val unseenMessages = messagesRef
            .whereEqualTo("receiverId", userId)
            .whereEqualTo("seen", false)
            .get()
            .await()

        // Step 3: Batch update messages (mark as seen)
        unseenMessages.documents.chunked(400).forEach { chunk ->
            firestore.runBatch { batch ->
                chunk.forEach { doc ->
                    batch.update(doc.reference, "seen", true)
                }
            }.await()
        }
    }

}
