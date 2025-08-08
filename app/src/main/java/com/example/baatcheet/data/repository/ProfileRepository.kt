package com.example.baatcheet.data.repository

import android.net.Uri
import com.example.baatcheet.data.model.User
import com.example.baatcheet.data.model.UserPresence
import com.example.baatcheet.data.network.FirebaseResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

interface ProfileRepository {
    suspend fun uploadProfile(uid: String, name: String, phoneNumber: String, imageUri: Uri?): FirebaseResult<Unit>

    suspend fun setUserOnline(userId: String)
    suspend fun setUserOffline(userId: String)
    suspend fun getUserById(userId: String): User?
     fun observePresence(userId: String): Flow<UserPresence>
}

class ProfileRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore,
    private val realtimeDb: FirebaseDatabase

) : ProfileRepository {

    override suspend fun uploadProfile(
        uid: String,
        name: String,
        phoneNumber: String,
        imageUri: Uri?
    ): FirebaseResult<Unit> {
        return try {
            val imageUrl = imageUri?.let {
                val ref = storage.reference.child("profileImages/$uid.jpg")
                ref.putFile(it).await()
                ref.downloadUrl.await().toString()
            }

            val date = Date(System.currentTimeMillis())
            val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            val userCreatedTime = formatter.format(date)
            val userMap = hashMapOf(
                "uid" to uid,
                "name" to name,
                "phone" to phoneNumber,
                "imageUrl" to imageUrl,
                "isOnline" to false,
                "createdAt" to userCreatedTime
            )

            firestore.collection("users").document(uid).set(userMap).await()
            FirebaseResult.Success(Unit)
        } catch (e: Exception) {
            FirebaseResult.Failure(e.message ?: "Unknown error")
        }
    }

    override suspend fun setUserOnline(userId: String) {
        val presenceRef = realtimeDb.getReference("presence/$userId")
        val connectedRef = realtimeDb.getReference(".info/connected")

        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    presenceRef.onDisconnect().setValue(
                        mapOf(
                            "state" to "offline",
                            "lastChanged" to ServerValue.TIMESTAMP
                        )
                    )
                    presenceRef.setValue(
                        mapOf(
                            "state" to "online",
                            "lastChanged" to ServerValue.TIMESTAMP
                        )
                    )
                    firestore.collection("users").document(userId)
                        .update("online", true)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override suspend fun setUserOffline(userId: String) {
        val lastSeen = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date())
        firestore.collection("users").document(userId)
            .update(
                mapOf(
                    "online" to false,
                    "lastSeen" to lastSeen
                )
            )
    }

    override  fun observePresence(userId: String): Flow<UserPresence> = callbackFlow {
        val userDocRef = firestore.collection("users").document(userId)
        val listener = userDocRef.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null) {
                close(error)
                return@addSnapshotListener
            }

            val online = snapshot.getBoolean("online") ?: false
            val lastSeen = snapshot.getString("lastSeen") ?: ""
            trySend(UserPresence(online, lastSeen))
        }

        awaitClose { listener.remove() }


    }
    override suspend fun getUserById(userId: String): User? {
        val snapshot = firestore.collection("users").document(userId).get().await()
        return snapshot.toObject(User::class.java)
    }

}