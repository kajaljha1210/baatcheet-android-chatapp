package com.example.baatcheet.ui.theme.navigation


enum class Screen {
    SPLASH,
    INTRO,
    HOME,
    LOGIN,
    OTP,
}
sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Intro : NavigationItem(Screen.INTRO.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object OTP : NavigationItem(Screen.OTP.name)
    object Home : NavigationItem(Screen.HOME.name)
}

