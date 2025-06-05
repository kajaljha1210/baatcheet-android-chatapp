package com.example.baatcheet.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.baatcheet.SplashScreen
import com.example.baatcheet.ui.screens.HomeScreen
import com.example.baatcheet.ui.screens.LoginScreen
import com.example.baatcheet.ui.screens.OTPScreen
import com.example.baatcheet.ui.screens.ProfileScreen
import com.example.baatcheet.ui.theme.screens.IntroScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Splash.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavigationItem.Intro.route) {
            IntroScreen(navController)
        }
        composable(NavigationItem.Login.route) {
            LoginScreen(navController)
        }
        composable(NavigationItem.OTP.route) {
            OTPScreen(navController)
        }
        composable(NavigationItem.OTP.route) {
            ProfileScreen(navController)
        }
        composable(NavigationItem.OTP.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.OTP.route) {
            HomeScreen(navController)
        }
    }
}