package com.example.mindbridge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.Counsellor
import com.example.mindbridge.ui.components.CounsellorCard
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.theme.*

@Composable
fun CounsellorsScreen(
    counsellors: List<Counsellor>,
    onBack: () -> Unit,
    onSelectCounsellor: (String) -> Unit,
    onBookCounsellor: (String) -> Unit,
    onCrisisClick: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("All") }
    val languages = listOf("All", "English", "Luganda", "Runyankore")

    val filteredList = counsellors.filter { c ->
        selectedLanguage == "All" || c.languages.contains(selectedLanguage)
    }

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = "Verified Counsellors",
                subtitle = "Licensed Ugandan Practitioners",
                onBack = onBack,
                onCrisisClick = onCrisisClick
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .testTag("counsellors_screen"),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
        ) {
            item {
                Text(
                    text = "Filter by Language",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MindBridgeNavy
                )
                LazyRow(
                    modifier = Modifier.padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(languages) { lang ->
                        FilterChip(
                            selected = selectedLanguage == lang,
                            onClick = { selectedLanguage = lang },
                            label = { Text(lang, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MindBridgeBlue,
                                selectedLabelColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }
            }

            items(filteredList) { counsellor ->
                CounsellorCard(
                    counsellor = counsellor,
                    onClick = { onSelectCounsellor(counsellor.id) },
                    onBookClick = { onBookCounsellor(counsellor.id) }
                )
            }
        }
    }
}
