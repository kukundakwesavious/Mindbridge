package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.PeerPost
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun PeerSupportScreen(
    posts: List<PeerPost>,
    onAddPost: (String) -> Unit,
    onBack: () -> Unit,
    onCrisisClick: () -> Unit
) {
    var newPostText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = "Peer Reflections",
                subtitle = "Anonymous Student Community",
                onBack = onBack,
                onCrisisClick = onCrisisClick
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .testTag("peer_support_screen"),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Share an Encouraging Word",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MindBridgeNavy
                        )
                        OutlinedTextField(
                            value = newPostText,
                            onValueChange = { newPostText = it },
                            placeholder = { Text("What helped you get through a tough week? (Posted anonymously)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("peer_input_field"),
                            shape = RoundedCornerShape(12.dp),
                            maxLines = 4
                        )
                        Button(
                            onClick = {
                                if (newPostText.isNotBlank()) {
                                    onAddPost(newPostText)
                                    newPostText = ""
                                }
                            },
                            enabled = newPostText.isNotBlank(),
                            modifier = Modifier
                                .align(Alignment.End)
                                .testTag("post_peer_button"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Share Anonymously")
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Recent Community Reflections",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MindBridgeNavy,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(posts) { post ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = post.author,
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeBlue,
                                fontSize = 13.sp
                            )
                            Text(
                                text = post.createdAt,
                                fontSize = 11.sp,
                                color = MindBridgeTextMuted
                            )
                        }

                        Text(
                            text = post.text,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MindBridgeNavy,
                            lineHeight = 22.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Likes",
                                tint = Color(0xFFF43F5E),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${post.likes + 1}",
                                fontSize = 12.sp,
                                color = MindBridgeTextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}
