package com.example.baatcheet.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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
import com.example.baatcheet.ui.components.AppButton
import com.example.baatcheet.ui.components.AppText
import com.example.baatcheet.ui.components.HeadingText
import com.example.baatcheet.ui.components.IntroAnim
import com.example.baatcheet.ui.theme.navigation.NavigationItem

@Composable
fun IntroScreen(navController: NavController? = null) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Scrollable content
                Column(
                    modifier = Modifier
                        .weight(1f)
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
                    IntroAnim()
                    Spacer(modifier = Modifier.height(8.dp))
                    AppText(
                        stringResource(id = R.string.app_description),
                        MaterialTheme.typography.bodyLarge,
                        TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Fixed button at bottom
                AppButton(
                    text = stringResource(id = R.string.start_button),
                    onClick = {
                        navController?.navigate(NavigationItem.Login.route)
                    },
                    icon = Icons.Default.ArrowForward,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
                Spacer(modifier = Modifier.height(20.dp)) // Safe spacing from bottom
            }
        }
    }
}


@Preview(
    name = "Small Phone",
    device = "spec:width=320dp,height=568dp",
    showSystemUi = true
)
@Preview(
    name = "Regular Phone",
    device = "spec:width=360dp,height=640dp",
    showSystemUi = true
)
@Preview(
    name = "Large Phone",
    device = "spec:width=400dp,height=800dp",
    showSystemUi = true
)
@Composable
fun IntroScreenPreview() {
    IntroScreen()
}

