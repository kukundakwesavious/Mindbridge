package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.FaithLeader
import com.example.mindbridge.data.model.ReferralRequest
import com.example.mindbridge.data.model.ReferralType
import com.example.mindbridge.ui.theme.*

@Composable
fun ReferralsScreen(
    referralTypes: List<ReferralType>,
    faithLeaders: List<FaithLeader>,
    activeReferrals: List<ReferralRequest>,
    onSelectType: (String) -> Unit,
    onCrisisClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MindBridgeBackground)
            .padding(horizontal = 16.dp)
            .testTag("referrals_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
    ) {
        item {
            Text(
                text = "Community Referrals",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy
            )
            Text(
                text = "Connecting you with trusted local Ugandan community pillars, faith leaders, and clinics",
                style = MaterialTheme.typography.bodyMedium,
                color = MindBridgeTextMuted,
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // Referral categories
        items(referralTypes) { refType ->
            val icon = when (refType.id) {
                "community" -> Icons.Default.Groups
                "faith" -> Icons.Default.Church
                "facility" -> Icons.Default.LocalHospital
                else -> Icons.Default.Warning
            }
            val accentColor = when (refType.tone) {
                "green" -> MindBridgeGreen
                "gold" -> Color(0xFFD97706)
                "red" -> CrisisRed
                else -> MindBridgeBlue
            }
            val bgAccent = when (refType.tone) {
                "green" -> MindBridgeLightGreen
                "gold" -> Color(0xFFFEF3C7)
                "red" -> CrisisRedBg
                else -> MindBridgeLightBlue
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectType(refType.id) }
                    .testTag("referral_type_${refType.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(bgAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = refType.title,
                            tint = accentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = refType.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy
                        )
                        Text(
                            text = refType.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MindBridgeTextMuted
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Open",
                        tint = MindBridgeTextMuted
                    )
                }
            }
        }

        // Active referral requests
        if (activeReferrals.isNotEmpty()) {
            item {
                Text(
                    text = "My Active Referral Requests",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MindBridgeNavy,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(activeReferrals) { req ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = req.provider,
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeNavy,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "Requested: ${req.createdAt}",
                                fontSize = 12.sp,
                                color = MindBridgeTextMuted
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFEF3C7)
                        ) {
                            Text(
                                text = req.status,
                                color = Color(0xFFB45309),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Faith Leaders Spotlight
        item {
            Text(
                text = "Faith Leaders & Pastoral Counsel",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(faithLeaders) { leader ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = leader.avatar,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309),
                            fontSize = 16.sp
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = leader.name,
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "${leader.role} • ${leader.area}",
                            fontSize = 12.sp,
                            color = MindBridgeTextMuted
                        )
                        Text(
                            text = "⭐ ${leader.rating} (${leader.reviews} community reviews)",
                            fontSize = 12.sp,
                            color = Color(0xFFD97706),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }

                    Button(
                        onClick = { onSelectType("faith") },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
                    ) {
                        Text("Connect", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
