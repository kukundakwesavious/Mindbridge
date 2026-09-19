package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun SignUpScreen(
    onBack: () -> Unit,
    onRegistered: (name: String, email: String, district: String, university: String) -> Unit,
    onCrisisClick: () -> Unit
) {
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("Kampala") }
    var university by remember { mutableStateOf("Makerere University") }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = "Create Profile",
                subtitle = "MindBridge Uganda",
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
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(scrollState)
                .testTag("signup_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Welcome to the Community",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy
            )

            Text(
                text = "Personalize your experience. All fields are confidential and can be updated anytime.",
                style = MaterialTheme.typography.bodyMedium,
                color = MindBridgeTextMuted
            )

            OutlinedTextField(
                value = displayName,
                onValueChange = { displayName = it },
                label = { Text("Display Name or Nickname") },
                placeholder = { Text("e.g., Kato or Aine") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("name_input"),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email (Optional)") },
                placeholder = { Text("For session confirmations") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("email_input"),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = university,
                onValueChange = { university = it },
                label = { Text("University or School (Optional)") },
                placeholder = { Text("e.g., Makerere, Kyambogo, Kabale") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("university_input"),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = district,
                onValueChange = { district = it },
                label = { Text("District in Uganda") },
                placeholder = { Text("e.g., Kampala, Mbarara, Gulu") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("district_input"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val finalName = if (displayName.isBlank()) "Friend" else displayName
                    onRegistered(finalName, email, district, university)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_signup_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
            ) {
                Text(
                    text = "Complete & Enter",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
