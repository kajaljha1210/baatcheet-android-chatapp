package com.example.baatcheet.data.repository

import android.net.Uri
import com.example.baatcheet.data.network.FirebaseResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

interface ProfileRepository {
    suspend fun uploadProfile(uid: String, name: String, phoneNumber: String, imageUri: Uri?): FirebaseResult<Unit>
}

class ProfileRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
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

}