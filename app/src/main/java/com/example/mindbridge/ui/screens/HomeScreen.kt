package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.Booking
import com.example.mindbridge.data.model.ContentItem
import com.example.mindbridge.data.model.UserSession
import com.example.mindbridge.ui.components.ContentCard
import com.example.mindbridge.ui.theme.*

@Composable
fun HomeScreen(
    session: UserSession?,
    upcomingBooking: Booking?,
    featuredContent: List<ContentItem>,
    onAmaniClick: () -> Unit,
    onBookClick: () -> Unit,
    onCrisisClick: () -> Unit,
    onPeerSupportClick: () -> Unit,
    onContentClick: (String) -> Unit,
    onSessionClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MindBridgeBackground)
            .padding(horizontal = 16.dp)
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        // Top Greeting & Anonymous ID
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Oli otya, ${session?.displayName ?: "Friend"}!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MindBridgeNavy
                    )
                    Text(
                        text = "How is your heart feeling today?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MindBridgeTextMuted
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MindBridgeLightBlue,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = session?.anonymousId ?: "UG-4821",
                        color = MindBridgeBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Emergency SOS Alert banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onCrisisClick)
                    .testTag("crisis_alert_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CrisisRedBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(CrisisRed.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "SOS",
                            tint = CrisisRed,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Need Immediate Support?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = CrisisRed
                        )
                        Text(
                            text = "Tap here for toll-free Uganda youth helplines & calm exercises",
                            fontSize = 12.sp,
                            color = CrisisRed.copy(alpha = 0.85f)
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Open",
                        tint = CrisisRed
                    )
                }
            }
        }

        // Quick action buttons
        item {
            Text(
                text = "Quick Actions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionCard(
                    title = "Talk to Amani",
                    subtitle = "24/7 AI Companion",
                    icon = Icons.Default.AutoAwesome,
                    color = MindBridgeBlue,
                    bgColor = MindBridgeLightBlue,
                    onClick = onAmaniClick,
                    modifier = Modifier.weight(1f)
                )

                QuickActionCard(
                    title = "Book Therapist",
                    subtitle = "Verified Ugandan counsellors",
                    icon = Icons.Default.CalendarMonth,
                    color = MindBridgeGreen,
                    bgColor = MindBridgeLightGreen,
                    onClick = onBookClick,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Upcoming Session Card if any
        if (upcomingBooking != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSessionClick(upcomingBooking.id) }
                        .testTag("upcoming_booking_card"),
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
                            Text(
                                text = "Upcoming Appointment",
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeBlue,
                                fontSize = 13.sp
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFE0F2FE)
                            ) {
                                Text(
                                    text = upcomingBooking.status,
                                    color = MindBridgeBlue,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Text(
                            text = upcomingBooking.counsellorName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy,
                            modifier = Modifier.padding(top = 6.dp)
                        )

                        Text(
                            text = "${upcomingBooking.mode} • ${upcomingBooking.date} at ${upcomingBooking.time}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MindBridgeTextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        Button(
                            onClick = { onSessionClick(upcomingBooking.id) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
                        ) {
                            Text("Open Consultation Room")
                        }
                    }
                }
            }
        }

        // Daily reflection / quote
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF9C3)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Quote",
                            tint = Color(0xFF854D0E),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Daily Grounding",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF854D0E),
                            fontSize = 13.sp
                        )
                    }
                    Text(
                        text = "\"Peace does not mean to be in a place where there is no noise or trouble. It means to be in the midst of those things and still be calm in your heart.\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF713F12),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        }

        // Recommended psychoeducation library
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recommended for You",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MindBridgeNavy
                )
                TextButton(onClick = { onContentClick(featuredContent.firstOrNull()?.id ?: "p1") }) {
                    Text("Explore All", color = MindBridgeBlue)
                }
            }
        }

        items(featuredContent.take(2)) { item ->
            ContentCard(
                item = item,
                onClick = { onContentClick(item.id) }
            )
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    bgColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("action_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MindBridgeNavy
            )

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = MindBridgeTextMuted,
                lineHeight = 14.sp
            )
        }
    }
}
