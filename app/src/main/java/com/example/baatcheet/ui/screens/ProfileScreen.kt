package com.example.baatcheet.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.baatcheet.R
import com.example.baatcheet.ui.components.AppButton
import com.example.baatcheet.ui.components.HeadingText
import com.example.baatcheet.ui.components.NormalText
import com.example.baatcheet.ui.components.TermText
import com.example.baatcheet.ui.theme.white

@Composable
fun ProfileScreen(navController: NavController? = null) {
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
                stringResource(id = R.string.otp),
                MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(20.dp))

            NormalText(stringResource(id = R.string.otp_subtext), TextAlign.Start)

            Spacer(modifier = Modifier.height(10.dp))
            TermText(stringResource(id = R.string.resend), TextAlign.Center, 12.sp)

            AppButton(stringResource(id = R.string.otp), onClick = {})
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}