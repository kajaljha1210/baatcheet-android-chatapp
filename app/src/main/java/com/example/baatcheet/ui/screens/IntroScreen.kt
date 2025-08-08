package com.example.baatcheet.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baatcheet.R
import com.example.baatcheet.ui.components.AppButton
import com.example.baatcheet.ui.components.AppText
import com.example.baatcheet.ui.components.HeadingText
import com.example.baatcheet.ui.components.IntroAnim
import com.example.baatcheet.ui.navigation.NavigationItem
import com.example.baatcheet.ui.viewmodel.IntroViewModel

@Composable
fun IntroScreen(navController: NavController? = null, viewModel: IntroViewModel? = null) {
    val composition = viewModel?.lottieComposition?.collectAsState()?.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp) // Leave room for button
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            HeadingText(
                stringResource(id = R.string.intro_heading),
                MaterialTheme.typography.headlineSmall
            )
            HeadingText(
                stringResource(id = R.string.app_intro),
                MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(10.dp))

            AppText(
                stringResource(id = R.string.intro_subheading),
                MaterialTheme.typography.bodyLarge,
                TextAlign.Start
            )
            Spacer(modifier = Modifier.height(2.dp))

            if (composition != null) {
                IntroAnim(composition)
            }

            Spacer(modifier = Modifier.height(8.dp))

            AppText(
                stringResource(id = R.string.app_description),
                MaterialTheme.typography.bodyLarge,
                TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        // ✅ Fixed bottom button — no need to set fillMaxWidth or height
        AppButton(
            text = stringResource(id = R.string.start_button),
            onClick = {
                navController?.navigate(NavigationItem.Login.route)
            },
            icon = Icons.Default.ArrowForward,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
        IntroScreen()

}