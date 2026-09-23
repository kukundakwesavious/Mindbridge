package com.example.mindbridge.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.ui.theme.*
import com.example.mindbridge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onRegistered: (name: String, email: String, district: String, university: String, accountType: String) -> Unit,
    onCrisisClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    val westernUgandaUniversities = listOf(
        "Mbarara University of Science and Technology (MUST)",
        "Kabale University",
        "Mountains of the Moon University",
        "Bishop Stuart University",
        "Valley University of Science and Technology",
        "Ibanda University",
        "Metropolitan International University",
        "Uganda Pentecostal University",
        "Ankole Western University"
    )
    val accountTypes = listOf("Student", "Staff", "Other")

    var selectedUniversity by remember { mutableStateOf(westernUgandaUniversities[0]) }
    var selectedAccountType by remember { mutableStateOf(accountTypes[0]) }

    var uniDropdownExpanded by remember { mutableStateOf(false) }
    var typeDropdownExpanded by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf<String?>(null) }
    var isGoogleLoading by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
                .testTag("signup_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Designed illustration for the login/signup page
            Image(
                painter = painterResource(id = R.drawable.ic_login_illustration),
                contentDescription = "Login Illustration",
                modifier = Modifier
                    .size(130.dp)
                    .padding(bottom = 12.dp)
            )

            Text(
                text = "Create your account",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Join our safe and anonymous community.",
                style = MaterialTheme.typography.bodyMedium,
                color = MindBridgeTextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
            )

            if (showError != null) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = CrisisRedBg),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = showError ?: "",
                        color = CrisisRed,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Username
            OutlinedTextField(
                value = username,
                onValueChange = { 
                    username = it
                    showError = null
                },
                label = { Text("Username / Pseudonym") },
                placeholder = { Text("Enter your username") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = MindBridgeTextMuted) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("name_input"),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MindBridgeBlue,
                    unfocusedBorderColor = MindBridgeBorder
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Account Type
            ExposedDropdownMenuBox(
                expanded = typeDropdownExpanded,
                onExpandedChange = { typeDropdownExpanded = !typeDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedAccountType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Account Type") },
                    leadingIcon = { Icon(Icons.Default.SupervisorAccount, contentDescription = null, tint = MindBridgeTextMuted) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeDropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MindBridgeBlue,
                        unfocusedBorderColor = MindBridgeBorder
                    )
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

            Spacer(modifier = Modifier.height(12.dp))

            // University Dropdown
            ExposedDropdownMenuBox(
                expanded = uniDropdownExpanded,
                onExpandedChange = { uniDropdownExpanded = !uniDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedUniversity,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("University (Western Uganda)") },
                    leadingIcon = { Icon(Icons.Default.School, contentDescription = null, tint = MindBridgeTextMuted) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uniDropdownExpanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MindBridgeBlue,
                        unfocusedBorderColor = MindBridgeBorder
                    )
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

            Spacer(modifier = Modifier.height(12.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    showError = null
                },
                label = { Text("Password") },
                placeholder = { Text("Create a password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = MindBridgeTextMuted) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MindBridgeBlue,
                    unfocusedBorderColor = MindBridgeBorder
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Confirm Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    showError = null
                },
                label = { Text("Confirm Password") },
                placeholder = { Text("Repeat your password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = MindBridgeTextMuted) },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MindBridgeBlue,
                    unfocusedBorderColor = MindBridgeBorder
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit
            Button(
                onClick = {
                    if (username.isBlank()) {
                        showError = "Please enter a valid username."
                    } else if (password.length < 8) {
                        showError = "Password must be at least 8 characters long."
                    } else if (password != confirmPassword) {
                        showError = "Passwords do not match."
                    } else {
                        onRegistered(username, "", "Western region", selectedUniversity, selectedAccountType)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("submit_signup_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue)
            ) {
                Text(
                    text = "Sign Up / Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(
                onClick = onCrisisClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Need Immediate Help? Access Crisis Support",
                    color = CrisisRed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun borderStroke(width: androidx.compose.ui.unit.Dp, color: Color) = 
    androidx.compose.foundation.BorderStroke(width, color)
