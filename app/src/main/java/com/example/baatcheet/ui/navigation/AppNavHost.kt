package com.example.baatcheet.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baatcheet.SplashScreen
import com.example.baatcheet.ui.screens.LoginScreen
import com.example.baatcheet.ui.theme.screens.IntroScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Intro.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen()
        }
        composable(NavigationItem.Intro.route) {
            IntroScreen()
        }
        composable(NavigationItem.Login.route) {
            LoginScreen()
        }
    }
}