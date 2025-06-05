package com.example.baatcheet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.baatcheet.R
import com.example.baatcheet.ui.theme.Poppins
import com.example.baatcheet.ui.theme.darkNavy


@Composable
fun HeadingText(text: String, style: TextStyle) {
    val gradientColors = listOf(
        Color(0xFF0f0c29), Color(0xFF302b63), Color(0xFF24243e)
    )

    Text(
        text = text, style = style.copy(
            brush = Brush.horizontalGradient(gradientColors),
            fontWeight = FontWeight.SemiBold,
            fontFamily = Poppins
        )
    )
}


@Composable
fun NormalText(text: String, align: TextAlign) {

    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = Poppins,
        textAlign = align,
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun TermText(text: String, align: TextAlign, size: TextUnit) {

    Text(
        text = text,
        fontSize = size,
        fontFamily = Poppins,
        textAlign = align,
        modifier = Modifier.fillMaxWidth()
    )

}


@Composable
fun IntroAnim() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.intro
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        composition, iterations = LottieConstants.IterateForever, isPlaying = true
    )


    LottieAnimation(
        composition = composition,
        progress = preloaderProgress,
        modifier = Modifier.height(400.dp)
    )
}


@Composable
fun StartButton(text: String, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.Bottom),
        colors = ButtonDefaults.buttonColors(
            containerColor = darkNavy, contentColor = MaterialTheme.colorScheme.surface
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 8.dp, pressedElevation = 12.dp
        ),
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Start",
            modifier = Modifier.size(20.dp),
        )

    }
}


@Composable
fun AppButton(text: String, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(Alignment.Bottom),
        colors = ButtonDefaults.buttonColors(
            containerColor = darkNavy, contentColor = MaterialTheme.colorScheme.surface
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 8.dp, pressedElevation = 12.dp
        ),
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

    }
}

@Composable
fun LoginField(value: String, onChange: (String) -> Unit) {
    var selectedCode by remember { mutableStateOf("🇮🇳 +91") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountryCodePicker(
            selectedCode = selectedCode,
            onCodeSelected = { selectedCode = it },
            modifier = Modifier.width(120.dp) // Compact width for country code
        )
        Spacer(modifier = Modifier.width(8.dp)) // Small gap
        TextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent),
            placeholder = {
                Text(
                    "Phone number",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontFamily = Poppins
                    )
                )
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent, // WhatsApp green underline
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color.Gray
            )
        )
    }
}

@Composable
fun CountryCodePicker(
    selectedCode: String,
    onCodeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val countryList = listOf(
        "🇮🇳 +91" to "India",
        "🇺🇸 +1" to "United States",
        "🇬🇧 +44" to "United Kingdom",
        "🇨🇦 +1" to "Canada",
        "🇦🇺 +61" to "Australia"
    )

    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        TextField(
            value = selectedCode,
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
                .background(Color.White, RoundedCornerShape(8.dp))
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