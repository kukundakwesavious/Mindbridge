package com.example.mindbridge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mindbridge.ui.navigation.Screen
import com.example.mindbridge.ui.screens.*
import com.example.mindbridge.ui.theme.MindBridgeTheme
import com.example.mindbridge.ui.viewmodel.MindBridgeViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MindBridgeViewModel by viewModels {
        MindBridgeViewModel.Factory(
            (application as MindBridgeApplication).repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindBridgeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MindBridgeAppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MindBridgeAppNavigation(viewModel: MindBridgeViewModel) {
    val navController = rememberNavController()
    val session by viewModel.session.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onContinue = {
                    if (session != null && session?.onboardingComplete == true) {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onContinueAnonymous = {
                    viewModel.loginAnonymous("Friend") {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                onBack = { navController.popBackStack() },
                onRegistered = { name, email, district, university ->
                    viewModel.loginWithCredentials(name, email, district, university) {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen(
                viewModel = viewModel,
                onNavigateToCounsellors = {
                    navController.navigate(Screen.Counsellors.route)
                },
                onNavigateToCounsellorDetail = { id ->
                    navController.navigate(Screen.CounsellorDetail.createRoute(id))
                },
                onNavigateToBooking = { id ->
                    navController.navigate(Screen.Booking.createRoute(id))
                },
                onNavigateToChat = { id ->
                    navController.navigate(Screen.Chat.createRoute(id))
                },
                onNavigateToSessionRoom = { id ->
                    navController.navigate(Screen.SessionRoom.createRoute(id))
                },
                onNavigateToContentDetail = { id ->
                    navController.navigate(Screen.ContentDetail.createRoute(id))
                },
                onNavigateToReferralDetail = { typeId ->
                    navController.navigate(Screen.ReferralDetail.createRoute(typeId))
                },
                onNavigateToCrisis = {
                    navController.navigate(Screen.Crisis.route)
                },
                onNavigateToPeerSupport = {
                    navController.navigate(Screen.PeerSupport.route)
                },
                onNavigateToLanguage = {
                    navController.navigate(Screen.Language.route)
                },
                onLogout = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Counsellors.route) {
            CounsellorsScreen(
                counsellors = viewModel.counsellors,
                onBack = { navController.popBackStack() },
                onSelectCounsellor = { id ->
                    navController.navigate(Screen.CounsellorDetail.createRoute(id))
                },
                onBookCounsellor = { id ->
                    navController.navigate(Screen.Booking.createRoute(id))
                },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(
            route = Screen.CounsellorDetail.route,
            arguments = listOf(navArgument("counsellorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("counsellorId") ?: ""
            val counsellor = viewModel.counsellors.find { it.id == id }
            CounsellorDetailScreen(
                counsellor = counsellor,
                onBack = { navController.popBackStack() },
                onBookClick = { cId ->
                    navController.navigate(Screen.Booking.createRoute(cId))
                },
                onChatClick = { cId ->
                    navController.navigate(Screen.Chat.createRoute(cId))
                },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(
            route = Screen.Booking.route,
            arguments = listOf(navArgument("counsellorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("counsellorId") ?: ""
            val counsellor = viewModel.counsellors.find { it.id == id }
            BookingScreen(
                counsellor = counsellor,
                onBack = { navController.popBackStack() },
                onConfirmBooking = { mode, date, time ->
                    viewModel.bookSession(id, mode, date, time) {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Main.route) { inclusive = false }
                        }
                    }
                },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("counsellorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("counsellorId") ?: ""
            val counsellor = viewModel.counsellors.find { it.id == id }
            val messages by viewModel.getChatMessages(id).collectAsState(initial = emptyList())

            ChatScreen(
                counsellor = counsellor,
                messages = messages,
                onSendMessage = { text ->
                    viewModel.sendChatMessage(id, text)
                },
                onBack = { navController.popBackStack() },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(
            route = Screen.SessionRoom.route,
            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString("bookingId") ?: ""
            val bookings by viewModel.bookings.collectAsState()
            val booking = bookings.find { it.id == bookingId } ?: bookings.firstOrNull()

            SessionRoomScreen(
                booking = booking,
                onLeaveRoom = { navController.popBackStack() },
                onOpenChat = { cId ->
                    navController.navigate(Screen.Chat.createRoute(cId))
                }
            )
        }

        composable(
            route = Screen.ContentDetail.route,
            arguments = listOf(navArgument("contentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val contentId = backStackEntry.arguments?.getString("contentId") ?: ""
            val item = viewModel.contentList.find { it.id == contentId }

            ContentDetailScreen(
                item = item,
                onBack = { navController.popBackStack() },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                },
                onBookClick = {
                    navController.navigate(Screen.Counsellors.route)
                }
            )
        }

        composable(
            route = Screen.ReferralDetail.route,
            arguments = listOf(navArgument("typeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val typeId = backStackEntry.arguments?.getString("typeId") ?: ""
            val refType = viewModel.referralTypes.find { it.id == typeId }

            ReferralDetailScreen(
                referralType = refType,
                onBack = { navController.popBackStack() },
                onSubmitRequest = { provider ->
                    viewModel.requestReferral(typeId, provider) {
                        // Request recorded
                    }
                },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(Screen.Crisis.route) {
            CrisisScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PeerSupport.route) {
            val peerPosts by viewModel.peerPosts.collectAsState()
            val sessionVal by viewModel.session.collectAsState()

            PeerSupportScreen(
                posts = peerPosts,
                onAddPost = { text ->
                    viewModel.addPeerPost(text, sessionVal?.displayName ?: "Anonymous Peer")
                },
                onBack = { navController.popBackStack() },
                onCrisisClick = {
                    navController.navigate(Screen.Crisis.route)
                }
            )
        }

        composable(Screen.Language.route) {
            val sessionVal by viewModel.session.collectAsState()

            LanguageScreen(
                currentLanguage = sessionVal?.language ?: "English",
                supportedLanguages = viewModel.supportedLanguages,
                onSelectLanguage = { lang ->
                    viewModel.updateLanguage(lang)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}
