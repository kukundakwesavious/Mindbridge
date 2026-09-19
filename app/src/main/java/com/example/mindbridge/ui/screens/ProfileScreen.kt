package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.mindbridge.data.model.UserSession
import com.example.mindbridge.ui.theme.*

@Composable
fun ProfileScreen(
    session: UserSession?,
    onLanguageClick: () -> Unit,
    onPeerSupportClick: () -> Unit,
    onCrisisClick: () -> Unit,
    onToggleNotifications: (Boolean) -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MindBridgeBackground)
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Profile card
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
                        .size(68.dp)
                        .clip(CircleShape)
                        .background(MindBridgeLightBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User",
                        tint = MindBridgeBlue,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Text(
                    text = session?.displayName ?: "Friend",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MindBridgeNavy
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MindBridgeLightBlue
                ) {
                    Text(
                        text = "ID: ${session?.anonymousId ?: "UG-4821"}",
                        color = MindBridgeBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "${session?.university ?: "Makerere University"} • ${session?.district ?: "Kampala"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MindBridgeTextMuted
                )
            }
        }

        // Settings Section
        Text(
            text = "App Settings & Preferences",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MindBridgeNavy
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                // Language selection
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onLanguageClick)
                        .padding(16.dp)
                        .testTag("language_setting_row"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = MindBridgeBlue)
                        Column {
                            Text(
                                text = "Language / Olulimi",
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeNavy,
                                fontSize = 14.sp
                            )
                            Text(
                                text = session?.language ?: "English",
                                fontSize = 12.sp,
                                color = MindBridgeTextMuted
                            )
                        }
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = "Open", tint = MindBridgeTextMuted)
                }

                Divider(color = MindBridgeBorder)

                // Peer reflections
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onPeerSupportClick)
                        .padding(16.dp)
                        .testTag("peer_support_setting_row"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Forum, contentDescription = null, tint = MindBridgeGreen)
                        Column {
                            Text(
                                text = "Peer Reflections",
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeNavy,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Anonymous student encouragement wall",
                                fontSize = 12.sp,
                                color = MindBridgeTextMuted
                            )
                        }
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = "Open", tint = MindBridgeTextMuted)
                }

                Divider(color = MindBridgeBorder)

                // Notifications Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = MindBridgeBlue)
                        Column {
                            Text(
                                text = "Session Reminders",
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeNavy,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Discreet notification before booked sessions",
                                fontSize = 12.sp,
                                color = MindBridgeTextMuted
                            )
                        }
                    }
                    Switch(
                        checked = session?.notifications ?: true,
                        onCheckedChange = onToggleNotifications
                    )
                }
            }
        }

        // Emergency button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onCrisisClick),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CrisisRedBg)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = CrisisRed)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Emergency Helplines (Uganda)",
                        fontWeight = FontWeight.Bold,
                        color = CrisisRed,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "24/7 Toll-free assistance & hospital directory",
                        fontSize = 12.sp,
                        color = CrisisRed.copy(alpha = 0.8f)
                    )
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CrisisRed)
            }
        }

        // Logout
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("logout_button"),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Switch Profile / Sign Out")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
