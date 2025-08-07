package com.example.baatcheet.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.baatcheet.ui.screens.ChatListScreen
import com.example.baatcheet.ui.screens.ChatScreen
import com.example.baatcheet.ui.screens.LoginScreen
import com.example.baatcheet.ui.screens.OTPScreen
import com.example.baatcheet.ui.screens.ProfileScreen
import com.example.baatcheet.ui.theme.screens.IntroScreen
import com.example.baatcheet.ui.utils.SessionManager
import com.example.baatcheet.ui.viewmodel.AuthViewmodel
import com.example.baatcheet.ui.viewmodel.ChatViewModel
import com.example.baatcheet.ui.viewmodel.IntroViewModel
import kotlinx.coroutines.flow.first

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val context = LocalContext.current
    val isLoggedIn = remember { mutableStateOf<Boolean?>(null) }
    val isProfileDone = remember { mutableStateOf<Boolean?>(null) }

    // Load session state once
    LaunchedEffect(Unit) {
        isLoggedIn.value = SessionManager.isLoggedIn(context)
        isProfileDone.value = SessionManager.isProfileDone(context).first()
    }

    // Show nothing until session is loaded
    if (isLoggedIn.value == null || isProfileDone.value == null) return

    // Set dynamic startDestination
    val startDestination = when {
        isLoggedIn.value == true && isProfileDone.value == true -> NavigationItem.Home.route
        isLoggedIn.value == true -> NavigationItem.Profile.route
        else -> NavigationItem.Intro.route
    }

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

        composable(NavigationItem.Intro.route) {
            val viewModel: IntroViewModel = hiltViewModel()
            IntroScreen(navController, viewModel)
        }

        composable(NavigationItem.Profile.route) {
            ProfileScreen(navController)
        }
        composable(NavigationItem.Home.route) {
            ChatListScreen(navController)
        }
        composable(
            route = "chat/{receiverId}",
            arguments = listOf(navArgument("receiverId") { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: ChatViewModel = hiltViewModel()
            val receiverId = backStackEntry.arguments?.getString("receiverId") ?: ""
            ChatScreen(viewModel = viewModel, receiverId = receiverId, navController = navController)
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
