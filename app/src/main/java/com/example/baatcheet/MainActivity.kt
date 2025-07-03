package com.example.baatcheet

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.baatcheet.ui.theme.BaatCheetTheme
import com.example.baatcheet.ui.theme.navigation.AppNavHost
import com.example.baatcheet.ui.theme.navigation.NavigationItem
import com.example.baatcheet.ui.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            BaatCheetTheme {
                AppNavHost(navController = rememberNavController())

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    BaatCheetTheme {
        AppNavHost(navController = rememberNavController())

    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.intro))
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2000)

        val isLoggedIn = SessionManager.isLoggedIn(context)
        val isProfileDone = SessionManager.isProfileDone(context).first()
        Log.d("Splash", "🔑 isLoggedIn = $isLoggedIn")

        if (isLoggedIn) {
            if (isProfileDone) {
                navController.navigate(NavigationItem.Home.route)
            } else {
                navController.navigate(NavigationItem.Profile.route)
            }
        } else {
            navController.navigate("auth") {
                popUpTo(NavigationItem.Splash.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(id = R.string.intro_image_desc),
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )

    }
}
