package com.example.baatcheet.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.baatcheet.R
import com.example.baatcheet.ui.components.AppText
import com.example.baatcheet.ui.components.ChatListItem
import com.example.baatcheet.ui.components.ChatSearchBar
import com.example.baatcheet.ui.utils.SessionManager
import com.example.baatcheet.ui.viewmodel.ChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    navController: NavController? = null,
    viewModel: ChatViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val filterOptions = listOf("All", "Unread", "Groups", "Favourites")
    var selectedFilter by remember { mutableStateOf("All") }

    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var uid by remember { mutableStateOf<String?>(null) }


    val chatList by viewModel.chatList.collectAsState()

    LaunchedEffect(Unit) {
        uid = SessionManager.getUid(context).first()
        uid?.let {
            viewModel.loadChatList(it)
        }
    }


    // BackHandler runs immediately on back press
    BackHandler {
        if (backPressedOnce) {
            (context as? Activity)?.finish()
        } else {
            backPressedOnce = true
        }
    }

    // Toast runs only when backPressedOnce becomes true
    LaunchedEffect(backPressedOnce) {
        if (backPressedOnce) {
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            delay(2000)
            backPressedOnce = false
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppText(
                        stringResource(id = R.string.greetingText),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Search Bar - full width
            ChatSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Filter Chips - LazyRow with adaptive spacing
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filterOptions) { item ->
                    FilterChip(
                        selected = item == selectedFilter,
                        onClick = { selectedFilter = item },
                        label = {
                            AppText(
                                text = item,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (item == selectedFilter) Color.White else Color.Black
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF25D366),
                            containerColor = Color(0xFFE0E0E0)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Chat List - LazyColumn fills available space
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {

                items(chatList) { chat ->
                    ChatListItem(
                        userName = chat.partnerName, // Replace with fetched name below
                        message = chat.lastMessage,
                        time = chat.lastMessageTimestamp, // create formatTime()
                        unreadCount = chat.unreadCount,
                        onClick = {
                            navController?.navigate("chat/${chat.partnerId}")
                        }
                    )
                    Divider(color = Color(0xFFE0E0E0), thickness = 0.5.dp)
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
fun ChatListScreenPreview() {
    ChatListScreen()
}