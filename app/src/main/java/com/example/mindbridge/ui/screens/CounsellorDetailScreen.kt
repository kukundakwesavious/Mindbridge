package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.Counsellor
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun CounsellorDetailScreen(
    counsellor: Counsellor?,
    onBack: () -> Unit,
    onBookClick: (String) -> Unit,
    onChatClick: (String) -> Unit,
    onCrisisClick: () -> Unit
) {
    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = counsellor?.name ?: "Counsellor",
                subtitle = counsellor?.role,
                onBack = onBack,
                onCrisisClick = onCrisisClick
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        if (counsellor == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Counsellor not found", color = MindBridgeTextMuted)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
                    .testTag("counsellor_detail_screen"),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile header card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(MindBridgeLightBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = counsellor.avatarInitials,
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeBlue,
                                fontSize = 24.sp
                            )
                        }

                        Text(
                            text = counsellor.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy
                        )

                        Text(
                            text = counsellor.role,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MindBridgeBlue,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFEAB308), modifier = Modifier.size(18.dp))
                            Text(
                                text = "${counsellor.rating} (${counsellor.reviews} student reviews)",
                                fontWeight = FontWeight.SemiBold,
                                color = MindBridgeNavy,
                                fontSize = 13.sp
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (counsellor.available) MindBridgeLightGreen else Color(0xFFF1F5F9)
                        ) {
                            Text(
                                text = if (counsellor.available) "● Available for Consultations" else "○ Currently In Session",
                                color = if (counsellor.available) MindBridgeGreen else MindBridgeTextMuted,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Speciality & Languages
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Specialisation Area",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MindBridgeNavy
                        )
                        Text(
                            text = counsellor.speciality,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MindBridgeNavy
                        )

                        Divider(color = MindBridgeBorder)

                        Text(
                            text = "Languages Spoken",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MindBridgeNavy
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            counsellor.languages.forEach { lang ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MindBridgeLightBlue
                                ) {
                                    Text(
                                        text = lang,
                                        fontSize = 12.sp,
                                        color = MindBridgeBlue,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onChatClick(counsellor.id) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("counsellor_chat_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = "Chat", modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Send Message", color = MindBridgeBlue)
                    }

                    Button(
                        onClick = { onBookClick(counsellor.id) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("counsellor_book_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
                    ) {
                        Text("Book Session", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
