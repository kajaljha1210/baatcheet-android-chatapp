package com.example.baatcheet.ui.utils

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.security.SecureRandom

object SessionManager {

    private const val TAG = "SessionManager"

    private const val PREF_NAME = "session_prefs"
    private const val UID_BYTE_LENGTH = 12
    private const val UID_TRIM_LENGTH = 16

    private val Context.dataStore by preferencesDataStore(PREF_NAME)

    private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    private val KEY_PHONE = stringPreferencesKey("phone_number")
    private val KEY_UID = stringPreferencesKey("user_uid")
    private val KEY_PROFILE_DONE = booleanPreferencesKey("is_profile_done")

    suspend fun setLogin(context: Context, isLoggedIn: Boolean, phone: String) {
        Log.d(TAG, "🛠 setLogin() called with phone=$phone, isLoggedIn=$isLoggedIn")
        val existingUid = generateRandomUid()
        context.dataStore.edit { prefs ->
            Log.d(TAG, "📝 Writing to DataStore...")
            prefs[KEY_IS_LOGGED_IN] = isLoggedIn
            prefs[KEY_PHONE] = phone
            prefs[KEY_UID] = existingUid
            Log.d(TAG, "✅ Data written: UID=$existingUid, isLoggedIn=$isLoggedIn, phone=$phone")
        }
        Log.d(TAG, "🏁 setLogin() finished")
    }

    fun generateRandomUid(): String {
        val randomBytes = ByteArray(UID_BYTE_LENGTH)
        SecureRandom().nextBytes(randomBytes)
        return Base64.encodeToString(randomBytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            .take(UID_TRIM_LENGTH)
    }

    suspend fun setProfileDone(context: Context, done: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_PROFILE_DONE] = done
        }
    }

    fun isProfileDone(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[KEY_PROFILE_DONE] ?: false }

    fun getUid(context: Context): Flow<String?> =
        context.dataStore.data.map { it[KEY_UID] }

    suspend fun isLoggedIn(context: Context): Boolean {
        val loggedIn = context.dataStore.data.first()[KEY_IS_LOGGED_IN] ?: false
        Log.d(TAG, "🔍 isLoggedIn check: $loggedIn")
        return loggedIn
    }

    fun getPhoneNumber(context: Context): Flow<String?> =
        context.dataStore.data.map {
            val phone = it[KEY_PHONE]
            Log.d(TAG, "📞 Fetched phone number: $phone")
            phone
        }

    suspend fun clearSession(context: Context) {
        context.dataStore.edit { it.clear() }
        Log.d(TAG, "🚪 Session cleared (logout)")
    }
}
