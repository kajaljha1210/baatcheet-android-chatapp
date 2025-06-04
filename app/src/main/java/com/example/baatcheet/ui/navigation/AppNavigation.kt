package com.example.baatcheet.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


enum class Screen {
    SPLASH,
    INTRO,
    HOME,
    LOGIN,
}
sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Intro : NavigationItem(Screen.INTRO.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Login : NavigationItem(Screen.LOGIN.name)
}

