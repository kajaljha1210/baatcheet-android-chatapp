package com.example.baatcheet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baatcheet.ui.theme.Poppins


@Composable
fun CountryCodePicker(
    selectedFlag: String,
    onCodeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val countryList = listOf(
        Triple("🇮🇳", "+91", "India"),
        Triple("🇺🇸", "+1", "United States"),
        Triple("🇬🇧", "+44", "United Kingdom"),
        Triple("🇨🇦", "+1", "Canada"),
        Triple("🇦🇺", "+61", "Australia")
    )


    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        TextField(
            value = selectedFlag,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                    focusManager.clearFocus()
                },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color(0xFF00A884), // WhatsApp green
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF00A884)
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .heightIn(max = 300.dp) // Scrollable if many items
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
        ) {
            countryList.forEach { (code, country) ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = code,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.width(60.dp)
                            )
                            Text(
                                text = country,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    },
                    onClick = {
                        onCodeSelected(code)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun OTPInputRow(
    otp: List<String>,
    onOtpChange: (index: Int, value: String) -> Unit,
    error: Boolean,
    focusRequesters: List<FocusRequester>,
    focusManager: FocusManager
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(6) { index ->
                TextField(
                    value = otp.getOrNull(index) ?: "",
                    onValueChange = { value ->
                        if (value.length <= 1 && value.all { it.isDigit() }) {
                            onOtpChange(index, value)
                            if (value.isNotEmpty()) {
                                if (index < 5) {
                                    focusRequesters[index + 1].requestFocus()
                                } else {
                                    focusManager.clearFocus()
                                }
                            }
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(48.dp)
                        .height(56.dp)
                        .focusRequester(focusRequesters[index])
                        .align (Alignment.CenterVertically)
                        .onKeyEvent {
                            if (it.key == Key.Backspace && (otp.getOrNull(index)
                                    ?.isEmpty() != false) && index > 0
                            ) {
                                focusRequesters[index - 1].requestFocus()
                            }
                            false
                        },
                    isError = error,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.Gray
                    )
                )
            }
        }

        if (error) {
            Text(
                text = "Please enter a valid 6-digit OTP",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}