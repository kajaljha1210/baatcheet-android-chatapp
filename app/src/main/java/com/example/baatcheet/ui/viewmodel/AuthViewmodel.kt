package com.example.baatcheet.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baatcheet.data.network.AuthResponse
import com.example.baatcheet.data.network.Country
import com.example.baatcheet.data.network.FirebaseResult
import com.example.baatcheet.data.repository.AuthRepository
import com.example.baatcheet.data.repository.ProfileRepository
import com.example.baatcheet.ui.state.AuthState
import com.example.baatcheet.ui.state.UiEvent
import com.example.baatcheet.ui.state.UiState
import com.example.baatcheet.ui.theme.navigation.NavigationItem
import com.example.baatcheet.ui.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    @ApplicationContext private val context: Context

) : ViewModel() {
    companion object {
        private const val OTP_TIMER_DURATION = 60
    }
    var authState by mutableStateOf(AuthState())
        private set
    var uiState by mutableStateOf<UiState>(UiState.Idle)
        private set

    private val _timer = MutableStateFlow(OTP_TIMER_DURATION)
    val timer: StateFlow<Int> = _timer

    private var timerJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun startTimer() {
        timerJob?.cancel()
        _timer.value = OTP_TIMER_DURATION
        timerJob = viewModelScope.launch {
            while (_timer.value > 0) {
                delay(1000)
                _timer.value -= 1
            }
        }
    }

    fun resetOtpAndTimer() {
        authState = authState.copy(otp = List(6) { "" })
        startTimer()
    }

    fun onPhoneNumberChange(newPhone: String) {
        authState = authState.copy(phoneNumber = newPhone)
    }

    fun onCountryChange(newCountry: Country) {
        authState = authState.copy(selectedCountry = newCountry)
    }

    fun onOtpChange(index: Int, value: String) {
        val updatedOtp = authState.otp.toMutableList().apply {
            this[index] = value
        }
        authState = authState.copy(otp = updatedOtp)
    }

    fun onNameChanged(newName: String) {
        authState = authState.copy(name = newName)
    }


    private fun getFullPhoneNumber(): String {
        return authState.selectedCountry.dialCode + authState.phoneNumber
    }

    fun sendOtp() {
        handleAuthCall(
            apiCall = { authRepository.sendOtp(getFullPhoneNumber()) },
            onSuccess = {
                _eventFlow.emit(UiEvent.ShowToast("OTP sent successfully"))
                _eventFlow.emit(UiEvent.Navigate(NavigationItem.OTP.route)) // 🔁 Navigate after success
            })
    }

    fun verifyOtp(context: Context) {
        Log.d("DEBUG", "✅ verifyOtp() CALLED")
        val otp = authState.otp.joinToString("")
        handleAuthCall(
            apiCall = { authRepository.verifyOtp(getFullPhoneNumber(), otp) },
            onSuccess = {
                SessionManager.setLogin(context, true, getFullPhoneNumber())
                Log.d("DEBUG", "✅ SessionManager.setLogin() called")


                _eventFlow.emit(UiEvent.NavigateToHome)
            })
    }

    fun uploadProfile(
        context: Context,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            uiState = UiState.Loading
            val uid = SessionManager.getUid(context).first()
            val phoneNumber = SessionManager.getPhoneNumber(context).first()


            Log.d("DEBUG", "UID: $uid, Phone: $phoneNumber") // Add this

            if (uid == null) {
                uiState = UiState.Error("UID not found")
                onError("Something went wrong. Please login again.")
                return@launch
            }

            when (val result = profileRepository.uploadProfile(
                uid = uid.toString(),
                name = authState.name,
                phoneNumber = phoneNumber.toString(),
                imageUri = imageUri
            )) {
                is FirebaseResult.Success -> {
                    uiState = UiState.Success
                    onSuccess()
                }

                is FirebaseResult.Failure -> {
                    uiState = UiState.Error(result.message)
                    onError(result.message)
                }
            }
        }
    }

    fun completeProfileSetup(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            SessionManager.setProfileDone(context, true)
            onSuccess()
        }
    }

    private fun handleAuthCall(
        apiCall: suspend () -> AuthResponse,
        onSuccess: suspend () -> Unit
    ) {
        viewModelScope.launch {
            uiState = UiState.Loading

            val response = apiCall()
            Log.d("DEBUG", "Response: success=${response.success}, msg=${response.message}")

            if (response.success) {
                uiState = UiState.Success
                onSuccess()
            } else {
                uiState = UiState.Error(response.message)
                _eventFlow.emit(UiEvent.ShowToast(response.message))
            }
        }
    }

}
