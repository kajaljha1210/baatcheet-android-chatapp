package com.example.baatcheet.ui.theme.navigation


enum class Screen {
    SPLASH,
    INTRO,
    LOGIN,
    OTP,
    HOME,
    PROFILE,
    CHAT,
}
sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Intro : NavigationItem(Screen.INTRO.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object OTP : NavigationItem(Screen.OTP.name)
    object Profile : NavigationItem(Screen.PROFILE.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Chat : NavigationItem(Screen.CHAT.name)
}

