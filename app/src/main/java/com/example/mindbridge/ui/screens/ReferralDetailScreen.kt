package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.ReferralType
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun ReferralDetailScreen(
    referralType: ReferralType?,
    onBack: () -> Unit,
    onSubmitRequest: (provider: String) -> Unit,
    onCrisisClick: () -> Unit
) {
    var selectedProvider by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    val providers = when (referralType?.id) {
        "faith" -> listOf(
            "Pastor John Musinguzi (Kigezi Region)",
            "Sheikh Abdul (Kawempe Division, Kampala)",
            "Rev. Sister Mary (Rubaga Cathedral, Kampala)"
        )
        "community" -> listOf(
            "Local Youth Council (Makindye Division)",
            "Kampala Community Peer Circle",
            "Makerere Peer Counsellor Association"
        )
        "facility" -> listOf(
            "Mulago National Referral Hospital - Mental Health Unit",
            "Butabika National Referral Hospital Outpatient Clinic",
            "Makerere University Hospital Health Centre IV",
            "Mbarara Regional Referral Hospital"
        )
        else -> listOf(
            "Uganda Youth & Child Helpline - Sauti 116",
            "Police Emergency - 112 / 999",
            "Lifeline Uganda 24/7 Helpline"
        )
    }

    LaunchedEffect(providers) {
        if (providers.isNotEmpty() && selectedProvider.isEmpty()) {
            selectedProvider = providers.first()
        }
    }

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = referralType?.title ?: "Referral",
                subtitle = "MindBridge Uganda Network",
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
                .testTag("referral_detail_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (submitted) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MindBridgeLightGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = MindBridgeGreen,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Referral Request Submitted!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MindBridgeNavy
                        )
                        Text(
                            text = "A coordinator will connect with your anonymous profile to facilitate a warm introduction to $selectedProvider.",
                            fontSize = 13.sp,
                            color = MindBridgeNavy,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Button(
                            onClick = onBack,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MindBridgeGreen)
                        ) {
                            Text("Done")
                        }
                    }
                }
            } else {
                Text(
                    text = "Request a Warm Referral",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MindBridgeNavy
                )

                Text(
                    text = "Referrals connect you directly with trusted health workers, religious leaders, or local mentors who understand the youth context in Uganda.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MindBridgeTextMuted
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Choose Provider / Organisation",
                            fontWeight = FontWeight.Bold,
                            color = MindBridgeNavy,
                            fontSize = 14.sp
                        )

                        providers.forEach { provider ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedProvider == provider,
                                    onClick = { selectedProvider = provider }
                                )
                                Text(
                                    text = provider,
                                    fontSize = 13.sp,
                                    color = MindBridgeNavy,
                                    modifier = Modifier.padding(start = 6.dp)
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("What support are you looking for? (Optional)") },
                    placeholder = { Text("e.g., academic stress, family advice, medical checkup...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 4
                )

                Button(
                    onClick = {
                        onSubmitRequest(selectedProvider)
                        submitted = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_referral_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
                ) {
                    Text("Submit Confidential Request", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
