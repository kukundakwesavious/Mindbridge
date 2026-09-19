package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.Booking
import com.example.mindbridge.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SessionRoomScreen(
    booking: Booking?,
    onLeaveRoom: () -> Unit,
    onOpenChat: (String) -> Unit
) {
    var isMuted by remember { mutableStateOf(false) }
    var isVideoOff by remember { mutableStateOf(false) }
    var secondsInSession by remember { mutableStateOf(145) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            secondsInSession++
        }
    }

    val minutes = secondsInSession / 60
    val secs = secondsInSession % 60
    val timeFormatted = String.format("%02d:%02d", minutes, secs)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("session_room_screen")
    ) {
        // Top Room Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = booking?.counsellorName ?: "Dr. Grace Nakunda",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Encrypted • $timeFormatted",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MindBridgeGreen
            ) {
                Text(
                    text = "LIVE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // Main Video Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1E293B)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MindBridgeBlue.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Therapist",
                        tint = Color.White,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Text(
                    text = booking?.counsellorName ?: "Counsellor",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    text = "Safe & confidential space",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
            }

            // Self view preview PIP
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(width = 80.dp, height = 110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF334155)),
                contentAlignment = Alignment.Center
            ) {
                if (isVideoOff) {
                    Icon(
                        imageVector = Icons.Default.VideocamOff,
                        contentDescription = "Video off",
                        tint = Color.White.copy(alpha = 0.6f)
                    )
                } else {
                    Text(
                        text = "You",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Bottom Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mute Button
            IconButton(
                onClick = { isMuted = !isMuted },
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (isMuted) Color.White else Color(0xFF334155))
            ) {
                Icon(
                    imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = "Mute",
                    tint = if (isMuted) CrisisRed else Color.White
                )
            }

            // Video Button
            IconButton(
                onClick = { isVideoOff = !isVideoOff },
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (isVideoOff) Color.White else Color(0xFF334155))
            ) {
                Icon(
                    imageVector = if (isVideoOff) Icons.Default.VideocamOff else Icons.Default.Videocam,
                    contentDescription = "Camera",
                    tint = if (isVideoOff) CrisisRed else Color.White
                )
            }

            // Chat in Room Button
            IconButton(
                onClick = { booking?.counsellorId?.let { onOpenChat(it) } },
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF334155))
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Chat",
                    tint = Color.White
                )
            }

            // End Call Button
            IconButton(
                onClick = onLeaveRoom,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(CrisisRed)
                    .testTag("end_session_button")
            ) {
                Icon(
                    imageVector = Icons.Default.CallEnd,
                    contentDescription = "Leave",
                    tint = Color.White
                )
            }
        }
    }
}
