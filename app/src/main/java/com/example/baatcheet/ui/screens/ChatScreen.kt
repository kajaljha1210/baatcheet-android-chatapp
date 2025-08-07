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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.baatcheet.ui.components.ChatTopBar
import com.example.baatcheet.ui.components.Messages
import com.example.baatcheet.ui.utils.SessionManager
import com.example.baatcheet.ui.viewmodel.ChatViewModel
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun ChatScreen(
    viewModel: ChatViewModel? = null,
    receiverId: String? = "",
    navController: NavController? = null
) {
    if (viewModel == null || receiverId.isNullOrEmpty()) return

    val context = LocalContext.current
    var senderId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        senderId = SessionManager.getUid(context).firstOrNull()
    }

    val messages by viewModel.messages.collectAsState()
    val messageText by viewModel.messageText.collectAsState()

    // Initialize chat when senderId and receiverId are available
    LaunchedEffect(senderId, receiverId) {
        if (!senderId.isNullOrEmpty()) {
            viewModel.initializeChat(senderId!!, receiverId)
        }
    }

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
            // Message Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                LazyColumn(
                    reverseLayout = true
                ) {
                    items(messages.reversed()) { msg ->
                        Messages(
                            message = msg.text,
                            time = msg.timestamp,
                            isSentByMe = msg.senderId == senderId
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Bottom Input Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Emoji action */ }) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Emoji",
                        tint = Color.Gray
                    )
                }

                OutlinedTextField(
                    value = messageText,
                    onValueChange = viewModel::onMessageTextChange, // ✅ fix name
                    placeholder = { Text("Message", color = Color.Gray) },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFF075E54)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(4.dp))

                IconButton(onClick = {
                    if (!senderId.isNullOrEmpty()) {
                        viewModel.sendMessage(senderId!!, receiverId)
                    }
                }) {
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