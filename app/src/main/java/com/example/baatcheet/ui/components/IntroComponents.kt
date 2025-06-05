package com.example.baatcheet.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Start",
            modifier = Modifier.size(20.dp),
        )

    }
}


