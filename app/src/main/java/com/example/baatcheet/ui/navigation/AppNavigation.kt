package com.example.baatcheet.ui.navigation


enum class Screen {
    INTRO,
    LOGIN,
    OTP,
    PROFILE,
    HOME,
    CHAT,
}
sealed class NavigationItem(val route: String) {
    object Intro : NavigationItem(Screen.INTRO.name)
    object Login : NavigationItem(Screen.LOGIN.name)
    object OTP : NavigationItem(Screen.OTP.name)
    object Profile : NavigationItem(Screen.PROFILE.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Chat : NavigationItem(Screen.CHAT.name){
        const val routeWithArgument: String = "chat/{receiverId}"
        const val argument: String = "receiverId"
    }
}

