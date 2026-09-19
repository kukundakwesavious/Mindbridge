package com.example.mindbridge.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object SignUp : Screen("signup")
    object Main : Screen("main")
    object Counsellors : Screen("counsellors")
    object CounsellorDetail : Screen("counsellor_detail/{counsellorId}") {
        fun createRoute(counsellorId: String) = "counsellor_detail/$counsellorId"
    }
    object Booking : Screen("booking/{counsellorId}") {
        fun createRoute(counsellorId: String) = "booking/$counsellorId"
    }
    object Chat : Screen("chat/{counsellorId}") {
        fun createRoute(counsellorId: String) = "chat/$counsellorId"
    }
    object SessionRoom : Screen("session_room/{bookingId}") {
        fun createRoute(bookingId: String) = "session_room/$bookingId"
    }
    object ContentDetail : Screen("content_detail/{contentId}") {
        fun createRoute(contentId: String) = "content_detail/$contentId"
    }
    object ReferralDetail : Screen("referral_detail/{typeId}") {
        fun createRoute(typeId: String) = "referral_detail/$typeId"
    }
    object Crisis : Screen("crisis")
    object PeerSupport : Screen("peer_support")
    object Language : Screen("language")
    object AmaniChat : Screen("amani_chat")
}

enum class MainTab(val title: String) {
    Home("Home"),
    Sessions("Sessions"),
    Amani("Amani AI"),
    Library("Library"),
    Referrals("Referrals"),
    Profile("Profile")
}
