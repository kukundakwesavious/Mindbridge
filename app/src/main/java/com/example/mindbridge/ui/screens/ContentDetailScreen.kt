package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.ContentItem
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun ContentDetailScreen(
    item: ContentItem?,
    onBack: () -> Unit,
    onCrisisClick: () -> Unit,
    onBookClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = item?.title ?: "Guide",
                subtitle = item?.category,
                onBack = onBack,
                onCrisisClick = onCrisisClick
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        if (item == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Content not found", color = MindBridgeTextMuted)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .verticalScroll(scrollState)
                    .testTag("content_detail_screen"),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MindBridgeLightBlue
                    ) {
                        Text(
                            text = "${item.type} • ${item.time}",
                            color = MindBridgeBlue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = item.category,
                        color = MindBridgeTextMuted,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MindBridgeNavy
                )

                // Key takeaway / summary card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Summary",
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy,
                            fontSize = 13.sp
                        )
                        Text(
                            text = item.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MindBridgeNavy,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Divider(color = MindBridgeBorder)

                // Body sections
                item.body.forEachIndexed { index, paragraph ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Key Insight ${index + 1}",
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeBlue,
                                fontSize = 13.sp
                            )
                            Text(
                                text = paragraph,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MindBridgeNavy,
                                lineHeight = 22.sp,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Action to speak with therapist
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MindBridgeLightGreen)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Would you like to speak to someone?",
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeGreen,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Our verified counsellors can provide personalized support based on these practices.",
                            fontSize = 13.sp,
                            color = MindBridgeNavy
                        )
                        Button(
                            onClick = onBookClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MindBridgeGreen)
                        ) {
                            Text("Book Confidential Session")
                        }
                    }
                }
            }
        }
    }
}
