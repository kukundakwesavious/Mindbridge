package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.Booking
import com.example.mindbridge.ui.theme.*

@Composable
fun SessionsScreen(
    bookings: List<Booking>,
    onBookNewClick: () -> Unit,
    onSessionClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MindBridgeBackground)
            .padding(horizontal = 16.dp)
            .testTag("sessions_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "My Appointments",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MindBridgeNavy
                    )
                    Text(
                        text = "Private consultations with Ugandan therapists",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MindBridgeTextMuted
                    )
                }

                FilledTonalButton(
                    onClick = onBookNewClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MindBridgeLightBlue,
                        contentColor = MindBridgeBlue
                    ),
                    modifier = Modifier.testTag("book_new_session_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "New",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Book")
                }
            }
        }

        if (bookings.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MindBridgeTextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "No Appointments Booked Yet",
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Connect confidentially with qualified Ugandan clinical psychologists and counsellors.",
                            color = MindBridgeTextMuted,
                            fontSize = 13.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Button(
                            onClick = onBookNewClick,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
                        ) {
                            Text("Browse Counsellors")
                        }
                    }
                }
            }
        } else {
            items(bookings) { booking ->
                BookingCard(
                    booking = booking,
                    onClick = { onSessionClick(booking.id) }
                )
            }
        }
    }
}

@Composable
fun BookingCard(
    booking: Booking,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("booking_card_${booking.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (booking.mode.contains("Video")) Icons.Default.Videocam else Icons.Default.Chat,
                        contentDescription = booking.mode,
                        tint = MindBridgeBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = booking.mode,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MindBridgeBlue
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFE8F5E9)
                ) {
                    Text(
                        text = booking.status,
                        color = MindBridgeGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Text(
                text = booking.counsellorName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy,
                modifier = Modifier.padding(top = 10.dp)
            )

            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "📅 ${booking.date}",
                    fontSize = 13.sp,
                    color = MindBridgeTextMuted
                )
                Text(
                    text = "⏰ ${booking.time}",
                    fontSize = 13.sp,
                    color = MindBridgeTextMuted
                )
            }

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
            ) {
                Text("Enter Consultation Room")
            }
        }
    }
}
