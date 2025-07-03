package com.example.baatcheet.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baatcheet.ui.components.ChatTopBar
import com.example.baatcheet.ui.components.Messages
import com.example.baatcheet.ui.theme.white


@Composable
fun ChatScreen(navController: NavController? = null) {
    Scaffold(
        topBar = {
            ChatTopBar(navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
        ) {
//            // Chat Messages Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                LazyColumn(
                    reverseLayout = true // latest message at bottom
                ) {
                    items(20) {
                        Messages("Hi, How are you?", "22:30 pm", it % 2 == 0)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Bottom Input Bar (WhatsApp-style)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Emoji icon
                IconButton(onClick = { /* Emoji */ }) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Emoji",
                        tint = Color.Gray
                    )
                }

                // Input Field
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* your msg update */ },
                    placeholder = {
                        Text("Message", color = Color.Gray)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = white,
                        unfocusedContainerColor = white,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF075E54)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(4.dp))

                // Send button
                IconButton(onClick = { /* Send */ }) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color(0xFF075E54)
                    )
                }
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
fun ChatScreenPreview() {
    ChatScreen()
}