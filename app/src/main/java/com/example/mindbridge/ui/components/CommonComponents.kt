package com.example.mindbridge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindbridge.data.model.ContentItem
import com.example.mindbridge.data.model.Counsellor
import com.example.mindbridge.ui.navigation.MainTab
import com.example.mindbridge.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MindBridgeTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    onCrisisClick: (() -> Unit)? = null,
    subtitle: String? = null
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MindBridgeNavy
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MindBridgeTextMuted
                    )
                }
            }
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("top_bar_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MindBridgeNavy
                    )
                }
            }
        },
        actions = {
            if (onCrisisClick != null) {
                Surface(
                    onClick = onCrisisClick,
                    shape = RoundedCornerShape(16.dp),
                    color = CrisisRedBg,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .testTag("top_bar_crisis_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "SOS",
                            tint = CrisisRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "SOS Crisis",
                            color = CrisisRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun MindBridgeBottomBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        val items = listOf(
            Triple(MainTab.Home, Icons.Default.Home, "Home"),
            Triple(MainTab.Sessions, Icons.Default.CalendarMonth, "Sessions"),
            Triple(MainTab.Amani, Icons.Default.AutoAwesome, "Amani AI"),
            Triple(MainTab.Library, Icons.Default.MenuBook, "Library"),
            Triple(MainTab.Referrals, Icons.Default.ShareLocation, "Referrals"),
            Triple(MainTab.Profile, Icons.Default.Person, "Profile")
        )

        items.forEach { (tab, icon, label) ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MindBridgeBlue,
                    selectedTextColor = MindBridgeBlue,
                    unselectedIconColor = MindBridgeTextMuted,
                    unselectedTextColor = MindBridgeTextMuted,
                    indicatorColor = MindBridgeLightBlue
                ),
                modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
            )
        }
    }
}

@Composable
fun CounsellorCard(
    counsellor: Counsellor,
    onClick: () -> Unit,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("counsellor_card_${counsellor.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Avatar initials circle
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(MindBridgeLightBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = counsellor.avatarInitials,
                        fontWeight = FontWeight.Bold,
                        color = MindBridgeBlue,
                        fontSize = 18.sp
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = counsellor.name,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium,
                            color = MindBridgeNavy
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (counsellor.available) Color(0xFFE8F5E9) else Color(0xFFF1F5F9)
                        ) {
                            Text(
                                text = if (counsellor.available) "Available" else "Busy",
                                color = if (counsellor.available) MindBridgeGreen else MindBridgeTextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text(
                        text = counsellor.role,
                        style = MaterialTheme.typography.bodySmall,
                        color = MindBridgeBlue
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFEAB308),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${counsellor.rating} (${counsellor.reviews} reviews)",
                            fontSize = 12.sp,
                            color = MindBridgeTextMuted
                        )
                    }
                }
            }

            Text(
                text = "Speciality: ${counsellor.speciality}",
                style = MaterialTheme.typography.bodyMedium,
                color = MindBridgeNavy,
                modifier = Modifier.padding(top = 10.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    counsellor.languages.forEach { lang ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFF8FAFC)
                        ) {
                            Text(
                                text = lang,
                                fontSize = 11.sp,
                                color = MindBridgeTextMuted,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Button(
                    onClick = onBookClick,
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MindBridgeBlue),
                    modifier = Modifier.testTag("book_button_${counsellor.id}")
                ) {
                    Text("Book Session", fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun ContentCard(
    item: ContentItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("content_card_${item.id}"),
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
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (item.tone) {
                        "green" -> MindBridgeLightGreen
                        "pink" -> Color(0xFFFFE4E6)
                        "orange" -> Color(0xFFFFF7ED)
                        else -> MindBridgeLightBlue
                    }
                ) {
                    Text(
                        text = item.category,
                        color = when (item.tone) {
                            "green" -> MindBridgeGreen
                            "pink" -> Color(0xFFE11D48)
                            "orange" -> Color(0xFFEA580C)
                            else -> MindBridgeBlue
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = item.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MindBridgeTextMuted
                )
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MindBridgeNavy,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = item.summary,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Read Guide →",
                    color = MindBridgeBlue,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
