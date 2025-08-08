package com.example.baatcheet.ui.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baatcheet.R
import com.example.baatcheet.ui.components.AppButton
import com.example.baatcheet.ui.components.AppText
import com.example.baatcheet.ui.components.HeadingText
import com.example.baatcheet.ui.components.OTPInputRow
import com.example.baatcheet.ui.navigation.NavigationItem
import com.example.baatcheet.ui.utils.Validator
import com.example.baatcheet.ui.viewmodel.AuthViewmodel

@Composable
fun OTPScreen(
    navController: NavController? = null,
    viewModel: AuthViewmodel = hiltViewModel()
) {
    val context = LocalContext.current.applicationContext
    val otp = viewModel.authState.otp
    var error by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequesters = List(6) { FocusRequester() }
    val timer by viewModel.timer.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp) // Space for fixed button
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            HeadingText(
                stringResource(id = R.string.otp),
                MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            AppText(
                stringResource(id = R.string.otp_subtext),
                MaterialTheme.typography.bodyLarge,
                TextAlign.Start
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OTPInputRow(
                    otp = otp,
                    onOtpChange = { index, value ->
                        viewModel.onOtpChange(index, value)
                    },
                    error = error,
                    focusRequesters = focusRequesters,
                    focusManager = focusManager
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (timer > 0) {
                    AppText(
                        text = "Resend OTP in $timer sec",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                } else {
                    TextButton(onClick = {
                        viewModel.resetOtpAndTimer()
                        focusRequesters[0].requestFocus()
                        error = false
                    }) {
                        Text("Resend OTP")
                    }
                }
            }
        }

        // ✅ Fixed Bottom Button
        AppButton(
            text = stringResource(id = R.string.otp),
            onClick = {
                val enteredOtp = otp.joinToString("")
                if (Validator.isOTPValid(enteredOtp)) {
                    viewModel.verifyOtp(context)
                    error = false
                    navController?.navigate(NavigationItem.Profile.route) {
                        popUpTo(NavigationItem.Intro.route) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    error = true
                }
            },
            enabled = otp.joinToString("").length == 6 && otp.all { it.length == 1 } && !error,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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
fun OTPScreenPreview() {
    OTPScreen()
}