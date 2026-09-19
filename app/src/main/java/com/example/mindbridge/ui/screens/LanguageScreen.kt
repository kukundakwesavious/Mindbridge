package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun LanguageScreen(
    currentLanguage: String,
    supportedLanguages: List<String>,
    onSelectLanguage: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = "Language Preferences",
                subtitle = "Londa Olulimi lwo",
                onBack = onBack
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .testTag("language_screen"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Choose Your Preferred Language",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy
            )

            Text(
                text = "MindBridge provides culturally contextualized mental health resources in multiple Ugandan languages.",
                style = MaterialTheme.typography.bodyMedium,
                color = MindBridgeTextMuted
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    supportedLanguages.forEachIndexed { index, lang ->
                        val isSelected = currentLanguage == lang
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectLanguage(lang)
                                    onBack()
                                }
                                .padding(16.dp)
                                .testTag("language_item_$lang"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = lang,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 15.sp,
                                    color = if (isSelected) MindBridgeBlue else MindBridgeNavy
                                )
                                Text(
                                    text = when (lang) {
                                        "Luganda" -> "Okwogera n'okusoma mu Luganda"
                                        "Runyankore" -> "Okugamba n'okushoma omu Runyankore"
                                        "Swahili" -> "Kiswahili cha Afrika Mashariki"
                                        else -> "Default language"
                                    },
                                    fontSize = 12.sp,
                                    color = MindBridgeTextMuted
                                )
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MindBridgeBlue
                                )
                            }
                        }

                        if (index < supportedLanguages.size - 1) {
                            Divider(color = MindBridgeBorder)
                        }
                    }
                }
            }
        }
    }
}
