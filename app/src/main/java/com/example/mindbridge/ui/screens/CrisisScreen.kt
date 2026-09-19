package com.example.mindbridge.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun CrisisScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val helplines = listOf(
        Triple("Uganda Youth & Child Helpline (Sauti)", "116", "24/7 Toll-Free • English, Luganda, Swahili"),
        Triple("Butabika National Mental Health Helpline", "0800200600", "Toll-Free Psychiatric Support"),
        Triple("Uganda Police & Medical Emergency", "112", "Emergency response"),
        Triple("Lifeline Uganda Helpline", "0800220000", "Confidential crisis intervention")
    )

    // Breathing exercise state
    var breathingPhase by remember { mutableStateOf("Inhale (4s)") }
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = "Emergency & Crisis",
                subtitle = "Immediate Support • Uganda",
                onBack = onBack
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(scrollState)
                .testTag("crisis_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // High alert notice
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CrisisRedBg)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Crisis",
                        tint = CrisisRed,
                        modifier = Modifier.size(28.dp)
                    )
                    Column {
                        Text(
                            text = "You Are Not Alone",
                            fontWeight = FontWeight.Bold,
                            color = CrisisRed,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "If you or someone around you is in immediate danger or experiencing intense distress, reach out right now.",
                            fontSize = 12.sp,
                            color = MindBridgeNavy
                        )
                    }
                }
            }

            // Helplines list
            Text(
                text = "Uganda Emergency Helplines",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy
            )

            helplines.forEach { (name, phone, desc) ->
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
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = name,
                                fontWeight = FontWeight.Bold,
                                color = MindBridgeNavy,
                                fontSize = 14.sp
                            )
                            Text(
                                text = phone,
                                fontWeight = FontWeight.ExtraBold,
                                color = CrisisRed,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                            Text(
                                text = desc,
                                fontSize = 11.sp,
                                color = MindBridgeTextMuted
                            )
                        }

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CrisisRed),
                            modifier = Modifier.testTag("dial_$phone")
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Call")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Grounding & Breathing tool
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Spa, contentDescription = null, tint = MindBridgeGreen)
                        Text(
                            text = "Calming Box Breathing",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MindBridgeNavy
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .scale(scale)
                            .clip(CircleShape)
                            .background(MindBridgeLightGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Breathe",
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeGreen,
                            fontSize = 16.sp
                        )
                    }

                    Text(
                        text = "Follow the rhythm: Inhale for 4 seconds, gently hold for 4 seconds, exhale for 4 seconds, and rest.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MindBridgeTextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}
