package com.example.baatcheet.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baatcheet.R
import com.example.baatcheet.data.network.Country
import com.example.baatcheet.ui.components.AppButton
import com.example.baatcheet.ui.components.AppText
import com.example.baatcheet.ui.components.CountryCodePicker
import com.example.baatcheet.ui.components.HeadingText
import com.example.baatcheet.ui.components.NormalTextField
import com.example.baatcheet.ui.state.UiEvent
import com.example.baatcheet.ui.utils.Validator
import com.example.baatcheet.ui.viewmodel.AuthViewmodel

@Composable
fun LoginScreen(
    navController: NavController? = null,
    viewModel: AuthViewmodel
) {

    var phoneNumber = viewModel.authState.phoneNumber
    val selectedCountry = viewModel.authState.selectedCountry
    val isButtonEnabled = Validator.isPhoneNumberValid(phoneNumber)
    var context = LocalContext.current
    val countries = listOf(
        Country("🇮🇳", "+91", "IN"),
        Country("🇺🇸", "+1", "US"),
        Country("🇬🇧", "+44", "UK")
    )

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.NavigateToHome -> {
                    navController?.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }

                is UiEvent.Navigate -> {
                    navController?.navigate(event.route) {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    HeadingText(
                        stringResource(id = R.string.login),
                        MaterialTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    AppText(
                        stringResource(id = R.string.login_subheading),
                        MaterialTheme.typography.bodyLarge,
                        TextAlign.Start
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ✅ Responsive Row for Country Code and Phone Number
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Country Code Picker (30% width)
                        Box(
                            modifier = Modifier
                                .weight(0.3f)
                                .defaultMinSize(minWidth = 80.dp)
                        ) {
                            CountryCodePicker(
                                selectedFlag = selectedCountry.emoji,
                                onCodeSelected = { emoji ->
                                    val country = countries.find { it.emoji == emoji }
                                    if (country != null) {
                                        viewModel.onCountryChange(country)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Box(
                            modifier = Modifier.weight(0.7f)
                        ) {
                            NormalTextField(
                                value = viewModel.authState.phoneNumber,
                                onChange = {
                                    if (Validator.isPhoneNumberValid(it))
                                        viewModel.onPhoneNumberChange(it)
                                           },
                                hint = "Enter phone number",
                                isError = false,
                                keyboardType = KeyboardType.Number
                            )
                        }
                    }



                    Spacer(modifier = Modifier.height(10.dp))

                    AppText(
                        stringResource(id = R.string.terms_and_conditions),
                        MaterialTheme.typography.bodySmall,
                        TextAlign.Center
                    )
                }

                // Login Button
                AppButton(
                    stringResource(id = R.string.login),
                    onClick = {
                        viewModel.sendOtp()

                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )

                Spacer(modifier = Modifier.height(20.dp)) // bottom space
            }
        }
    }
}

