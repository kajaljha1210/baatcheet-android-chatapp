package com.example.baatcheet.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.baatcheet.SplashScreen
import com.example.baatcheet.ui.screens.ChatListScreen
import com.example.baatcheet.ui.screens.ChatScreen
import com.example.baatcheet.ui.screens.LoginScreen
import com.example.baatcheet.ui.screens.OTPScreen
import com.example.baatcheet.ui.screens.ProfileScreen
import com.example.baatcheet.ui.theme.screens.IntroScreen
import com.example.baatcheet.ui.viewmodel.AuthViewmodel

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
        navigation(startDestination = "login", route = "auth") {
            composable("login") { backStackEntry ->
                val viewModel = sharedAuthViewModel(navController, backStackEntry)
                LoginScreen(navController, viewModel)
            }
            composable("otp") { backStackEntry ->
                val viewModel = sharedAuthViewModel(navController, backStackEntry)
                OTPScreen(navController, viewModel)
            }
        }



        composable(NavigationItem.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavigationItem.Intro.route) {
            IntroScreen(navController)
        }

        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController)
        }
        composable(NavigationItem.Home.route) {
            ChatListScreen(navController)
        }
        composable(NavigationItem.Chat.route) {
            ChatScreen(navController)
        }
    }
}
@Composable
fun sharedAuthViewModel(
    navController: NavHostController,
    currentBackStackEntry: NavBackStackEntry
): AuthViewmodel {
    val parentEntry = remember(currentBackStackEntry) {
        navController.getBackStackEntry("auth")
    }
    return hiltViewModel(parentEntry)
}
