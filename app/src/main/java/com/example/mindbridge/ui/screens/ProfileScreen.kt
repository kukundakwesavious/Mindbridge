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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.UserSession
import com.example.mindbridge.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    session: UserSession?,
    westernUgandaUniversities: List<String>,
    accountTypes: List<String>,
    onUpdateProfile: (name: String, email: String?, district: String, university: String, accountType: String) -> Unit,
    onLanguageClick: () -> Unit,
    onPeerSupportClick: () -> Unit,
    onCrisisClick: () -> Unit,
    onToggleNotifications: (Boolean) -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()

    var nameInput by remember(session) { mutableStateOf(session?.displayName ?: "") }
    var emailInput by remember(session) { mutableStateOf(session?.email ?: "") }
    var districtInput by remember(session) { mutableStateOf(session?.district ?: "") }
    var selectedUniversity by remember(session) { mutableStateOf(session?.university ?: "") }
    var selectedAccountType by remember(session) { mutableStateOf(session?.accountType ?: "Student") }

    var uniDropdownExpanded by remember { mutableStateOf(false) }
    var typeDropdownExpanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var showSuccessSnackbar by remember { mutableStateOf(false) }

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

        // Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                        contentDescription = "User Avatar",
                        tint = MindBridgeBlue,
                        modifier = Modifier.size(36.dp)
                    )
                }

                if (!isEditing) {
                    Text(
                        text = session?.displayName ?: "Friend",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MindBridgeNavy,
                        textAlign = TextAlign.Center
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MindBridgeLightBlue
                    ) {
                        Text(
                            text = "Anonymous ID: ${session?.anonymousId ?: "UG-4821"}",
                            color = MindBridgeBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "${session?.accountType ?: "Student"} • ${session?.university ?: "MUST"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MindBridgeTextMuted,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Region: ${session?.district ?: "Western region"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MindBridgeTextMuted,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = { isEditing = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Update Profile Info", fontSize = 13.sp)
                    }
                } else {
                    // Editing Mode
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Display Pseudonym") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Email (Optional)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = districtInput,
                        onValueChange = { districtInput = it },
                        label = { Text("Location / District") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = typeDropdownExpanded,
                        onExpandedChange = { typeDropdownExpanded = !typeDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedAccountType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Account Type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeDropdownExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = typeDropdownExpanded,
                            onDismissRequest = { typeDropdownExpanded = false }
                        ) {
                            accountTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) },
                                    onClick = {
                                        selectedAccountType = type
                                        typeDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = uniDropdownExpanded,
                        onExpandedChange = { uniDropdownExpanded = !uniDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedUniversity,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("University (Western Uganda)") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uniDropdownExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = uniDropdownExpanded,
                            onDismissRequest = { uniDropdownExpanded = false }
                        ) {
                            westernUgandaUniversities.forEach { uni ->
                                DropdownMenuItem(
                                    text = { Text(uni) },
                                    onClick = {
                                        selectedUniversity = uni
                                        uniDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isEditing = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                onUpdateProfile(
                                    nameInput,
                                    emailInput.ifBlank { null },
                                    districtInput,
                                    selectedUniversity,
                                    selectedAccountType
                                )
                                isEditing = false
                                showSuccessSnackbar = true
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MindBridgeGreen)
                        ) {
                            Text("Save Updates")
                        }
                    }
                }

                if (showSuccessSnackbar) {
                    Text(
                        text = "Profile updated confidentially!",
                        color = MindBridgeGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Settings Section
        Text(
            text = "App Settings & Security",
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onLanguageClick)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = MindBridgeBlue)
                        Column {
                            Text(text = "Language / Olulimi", fontWeight = FontWeight.Bold, color = MindBridgeNavy, fontSize = 14.sp)
                            Text(text = session?.language ?: "English", fontSize = 12.sp, color = MindBridgeTextMuted)
                        }
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MindBridgeTextMuted)
                }

                HorizontalDivider(color = MindBridgeBorder)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onPeerSupportClick)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Forum, contentDescription = null, tint = MindBridgeGreen)
                        Column {
                            Text(text = "Peer Reflections Support Group", fontWeight = FontWeight.Bold, color = MindBridgeNavy, fontSize = 14.sp)
                            Text(text = "100% Anonymous Shared Encouragement Wall", fontSize = 12.sp, color = MindBridgeTextMuted)
                        }
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MindBridgeTextMuted)
                }

                HorizontalDivider(color = MindBridgeBorder)

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = MindBridgeBlue)
                        Column {
                            Text(text = "Session Reminders", fontWeight = FontWeight.Bold, color = MindBridgeNavy, fontSize = 14.sp)
                            Text(text = "Discreet notification before booked sessions", fontSize = 12.sp, color = MindBridgeTextMuted)
                        }
                    }
                    Switch(
                        checked = session?.notifications ?: true,
                        onCheckedChange = onToggleNotifications
                    )
                }
            }
        }

        // Emergency card
        Card(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onCrisisClick),
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
                    Text(text = "Emergency Helplines (Uganda)", fontWeight = FontWeight.Bold, color = CrisisRed, fontSize = 14.sp)
                    Text(text = "24/7 Toll-free assistance & hospital directory", fontSize = 12.sp, color = CrisisRed.copy(alpha = 0.8f))
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CrisisRed)
            }
        }

        // Logout
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Switch Profile / Sign Out")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
