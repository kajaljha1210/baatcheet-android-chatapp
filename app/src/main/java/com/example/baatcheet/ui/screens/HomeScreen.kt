package com.example.baatcheet.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baatcheet.ui.theme.white

@Composable
fun HomeScreen(navController: NavController? = null) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = white
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {


        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}