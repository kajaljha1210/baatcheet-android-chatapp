package com.example.baatcheet.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baatcheet.R
import com.example.baatcheet.ui.components.HeadingText
import com.example.baatcheet.ui.components.IntroAnim
import com.example.baatcheet.ui.components.NormalText
import com.example.baatcheet.ui.components.StartButton
import com.example.baatcheet.ui.theme.navigation.NavigationItem
import com.example.baatcheet.ui.theme.white

@Composable
fun IntroScreen(navController: NavController? = null) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            HeadingText(
                stringResource(id = R.string.intro_heading),
                MaterialTheme.typography.headlineSmall
            )
            HeadingText(
                stringResource(id = R.string.app_intro),
                MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.padding(10.dp))
            NormalText(stringResource(id = R.string.intro_subheading), TextAlign.Start)
            Spacer(modifier = Modifier.padding(2.dp))
            IntroAnim()
            Spacer(modifier = Modifier.padding(2.dp))
            NormalText(stringResource(id = R.string.app_description), TextAlign.Center)
            StartButton(stringResource(id = R.string.start_button), onClick = {
                navController?.navigate(NavigationItem.Login.route)
            })
        }

    }
}

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    IntroScreen()
}

