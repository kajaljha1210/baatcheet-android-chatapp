package com.example.baatcheet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
        text = text,
        style = style.copy(
            brush = Brush.horizontalGradient(gradientColors),
            fontWeight = FontWeight.SemiBold,
            fontFamily = Poppins
        )
    )
}

@Composable
fun AppText(
    text: String,
    style: TextStyle,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = Color.Black
) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = style.copy(color = color),
        textAlign = textAlign,
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
fun AppButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    iconContentDescription: String? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = darkNavy, // अपनी color value दें
            contentColor = MaterialTheme.colorScheme.surface
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
        if (icon != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}
@Composable
fun NormalTextField(
    value: String,
    onChange: (String) -> Unit,
    hint: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onChange,
        isError = isError,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontFamily = Poppins
                )
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = Poppins
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Gray,
            errorIndicatorColor = MaterialTheme.colorScheme.error,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}


@Composable
fun CircleImage(size: Dp, iconSize: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.LightGray), // placeholder background
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Default Profile",
            tint = Color.DarkGray,
            modifier = Modifier.size(iconSize)
        )

    }
}
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    // अगर आप चाहें तो shape भी fix कर सकते हैं
    shape: Shape = RoundedCornerShape(8.dp)
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = placeholder,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp,
                    fontFamily = Poppins // अपनी पसंद का font, memory[9]
                )
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        readOnly = readOnly,
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = Poppins // अपनी पसंद का font, memory[9]
        ),
        shape = shape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = Color.Gray,
            // textColor और बाकी colors भी अगर चाहें तो यहाँ fix कर सकते हैं
        )
    )
}
