package com.example.mindbridge.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.mindbridge.ui.components.MindBridgeBottomBar
import com.example.mindbridge.ui.components.MindBridgeTopBar
import com.example.mindbridge.ui.navigation.MainTab
import com.example.mindbridge.ui.theme.MindBridgeBackground
import com.example.mindbridge.ui.viewmodel.MindBridgeViewModel

@Composable
fun MainScreen(
    viewModel: MindBridgeViewModel,
    onNavigateToCounsellors: () -> Unit,
    onNavigateToCounsellorDetail: (String) -> Unit,
    onNavigateToBooking: (String) -> Unit,
    onNavigateToChat: (String) -> Unit,
    onNavigateToSessionRoom: (String) -> Unit,
    onNavigateToContentDetail: (String) -> Unit,
    onNavigateToReferralDetail: (String) -> Unit,
    onNavigateToCrisis: () -> Unit,
    onNavigateToPeerSupport: () -> Unit,
    onNavigateToLanguage: () -> Unit,
    onLogout: () -> Unit
) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val session by viewModel.session.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val peerPosts by viewModel.peerPosts.collectAsState()
    val amaniMessages by viewModel.amaniMessages.collectAsState()
    val referrals by viewModel.referrals.collectAsState()
    val isSendingAmani by viewModel.isSendingAmani.collectAsState()

    val topBarTitle = when (selectedTab) {
        MainTab.Home -> "MindBridge"
        MainTab.Sessions -> "My Appointments"
        MainTab.Amani -> "Amani AI"
        MainTab.Library -> "Psychoeducation"
        MainTab.Referrals -> "Referrals"
        MainTab.Profile -> "Profile & Settings"
    }

    val topBarSubtitle = when (selectedTab) {
        MainTab.Home -> "MindBridge Uganda"
        MainTab.Sessions -> "Consultations"
        MainTab.Amani -> "24/7 AI Companion"
        MainTab.Library -> "Guides & Articles"
        MainTab.Referrals -> "Community Links"
        MainTab.Profile -> session?.anonymousId ?: "Anonymous User"
    }

    Scaffold(
        topBar = {
            MindBridgeTopBar(
                title = topBarTitle,
                subtitle = topBarSubtitle,
                onCrisisClick = onNavigateToCrisis
            )
        },
        bottomBar = {
            MindBridgeBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        containerColor = MindBridgeBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                MainTab.Home -> {
                    HomeScreen(
                        session = session,
                        upcomingBooking = bookings.firstOrNull(),
                        featuredContent = viewModel.contentList,
                        onAmaniClick = { viewModel.selectTab(MainTab.Amani) },
                        onBookClick = onNavigateToCounsellors,
                        onCrisisClick = onNavigateToCrisis,
                        onPeerSupportClick = onNavigateToPeerSupport,
                        onContentClick = onNavigateToContentDetail,
                        onSessionClick = onNavigateToSessionRoom
                    )
                }
                MainTab.Sessions -> {
                    SessionsScreen(
                        bookings = bookings,
                        onBookNewClick = onNavigateToCounsellors,
                        onSessionClick = onNavigateToSessionRoom
                    )
                }
                MainTab.Amani -> {
                    AmaniChatScreen(
                        messages = amaniMessages,
                        isSending = isSendingAmani,
                        onSendMessage = { viewModel.sendAmaniMessage(it) },
                        onCrisisClick = onNavigateToCrisis
                    )
                }
                MainTab.Library -> {
                    LibraryScreen(
                        contentList = viewModel.contentList,
                        onContentClick = onNavigateToContentDetail
                    )
                }
                MainTab.Referrals -> {
                    ReferralsScreen(
                        referralTypes = viewModel.referralTypes,
                        faithLeaders = viewModel.faithLeaders,
                        activeReferrals = referrals,
                        onSelectType = onNavigateToReferralDetail,
                        onCrisisClick = onNavigateToCrisis
                    )
                }
                MainTab.Profile -> {
                    ProfileScreen(
                        session = session,
                        westernUgandaUniversities = viewModel.westernUgandaUniversities,
                        accountTypes = viewModel.accountTypes,
                        onUpdateProfile = { name, email, district, university, accountType ->
                            viewModel.updateProfile(name, email, district, university, accountType)
                        },
                        onLanguageClick = onNavigateToLanguage,
                        onPeerSupportClick = onNavigateToPeerSupport,
                        onCrisisClick = onNavigateToCrisis,
                        onToggleNotifications = { viewModel.toggleNotifications(it) },
                        onLogout = {
                            viewModel.logout {
                                onLogout()
                            }
                        }
                    )
                }
            }
        }
    }
}
