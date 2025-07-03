package com.example.baatcheet.ui.state

import com.example.baatcheet.data.network.Country

val countries = listOf(
    Country("🇮🇳", "+91", "IN"),
    Country("🇺🇸", "+1", "US"),
    Country("🇬🇧", "+44", "UK")
)


// 🧾 Form fields
data class AuthState(
    val selectedCountry: Country = countries[0],
    val phoneNumber: String = "",
    val name: String = "",
    val otp: List<String> = List(6) { "" },
    val timer: Int = 60
)

// 📺 UI state
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}

// 🚀 One-time event
sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    object NavigateToHome : UiEvent()
}