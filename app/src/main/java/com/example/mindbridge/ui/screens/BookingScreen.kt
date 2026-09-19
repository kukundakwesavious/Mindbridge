package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.Counsellor
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun BookingScreen(
    counsellor: Counsellor?,
    onBack: () -> Unit,
    onConfirmBooking: (mode: String, date: String, time: String) -> Unit,
    onCrisisClick: () -> Unit
) {
    var selectedMode by remember { mutableStateOf("Text chat") }
    var selectedDate by remember { mutableStateOf("Tomorrow") }
    var selectedTime by remember { mutableStateOf("2:00 PM") }

    val modes = listOf(
        Triple("Text chat", "Low bandwidth & discrete", Icons.Default.Chat),
        Triple("Voice call", "Direct conversation", Icons.Default.Call),
        Triple("Video call", "Face-to-face consultation", Icons.Default.Videocam)
    )

    val dates = listOf("Today", "Tomorrow", "Thursday", "Friday", "Saturday")
    val times = listOf("10:00 AM", "11:30 AM", "2:00 PM", "3:30 PM", "5:00 PM")

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = "Book Appointment",
                subtitle = counsellor?.name ?: "Counsellor",
                onBack = onBack,
                onCrisisClick = onCrisisClick
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
                .testTag("booking_screen"),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Counsellor banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column {
                        Text(
                            text = counsellor?.name ?: "Verified Counsellor",
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy,
                            fontSize = 15.sp
                        )
                        Text(
                            text = counsellor?.role ?: "Mental Health Specialist",
                            fontSize = 12.sp,
                            color = MindBridgeBlue
                        )
                    }
                }
            }

            // Mode selection
            Text(
                text = "1. Select Consultation Mode",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MindBridgeNavy
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                modes.forEach { (mode, desc, icon) ->
                    val isSelected = selectedMode == mode
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedMode = mode },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MindBridgeLightBlue else Color.White
                        ),
                        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(MindBridgeBlue)) else null
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = mode,
                                tint = if (isSelected) MindBridgeBlue else MindBridgeTextMuted
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = mode,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isSelected) MindBridgeBlue else MindBridgeNavy
                                )
                                Text(
                                    text = desc,
                                    fontSize = 12.sp,
                                    color = MindBridgeTextMuted
                                )
                            }
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedMode = mode }
                            )
                        }
                    }
                }
            }

            // Date Selection
            Text(
                text = "2. Choose Date",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MindBridgeNavy
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dates.take(4).forEach { date ->
                    val isSelected = selectedDate == date
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedDate = date },
                        label = { Text(date, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MindBridgeBlue,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            // Time Selection
            Text(
                text = "3. Choose Time Slot (EAT)",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MindBridgeNavy
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                times.take(3).forEach { time ->
                    val isSelected = selectedTime == time
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedTime = time },
                        label = { Text(time, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MindBridgeBlue,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    onConfirmBooking(selectedMode, selectedDate, selectedTime)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("confirm_booking_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
            ) {
                Text(
                    text = "Confirm Booking ($selectedMode)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
