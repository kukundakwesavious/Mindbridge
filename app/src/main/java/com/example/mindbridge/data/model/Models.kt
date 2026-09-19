package com.example.mindbridge.data.model

data class UserSession(
    val anonymousId: String = "UG-${(1000..9999).random()}",
    val displayName: String = "Friend",
    val email: String? = null,
    val district: String = "Kampala",
    val university: String = "Makerere University",
    val language: String = "English",
    val notifications: Boolean = true,
    val onboardingComplete: Boolean = true
)

data class Counsellor(
    val id: String,
    val name: String,
    val role: String,
    val languages: List<String>,
    val speciality: String,
    val rating: Double,
    val reviews: Int,
    val available: Boolean,
    val avatarInitials: String,
    val imageDrawableName: String = "avatar_grace"
)

data class Booking(
    val id: String,
    val counsellorId: String,
    val counsellorName: String,
    val mode: String, // "Text chat", "Voice call", "Video call"
    val date: String,
    val time: String,
    val status: String = "Confirmed",
    val createdAt: String
)

data class ReferralRequest(
    val id: String,
    val type: String,
    val provider: String,
    val status: String = "Pending match",
    val createdAt: String
)

data class ChatMessage(
    val id: String,
    val counsellorId: String,
    val sender: String, // "user" or "counsellor"
    val text: String,
    val time: String
)

data class PeerPost(
    val id: String,
    val author: String,
    val text: String,
    val createdAt: String,
    val likes: Int = 0
)

data class ContentItem(
    val id: String,
    val title: String,
    val type: String, // "Article", "Guide"
    val time: String,
    val category: String,
    val tone: String,
    val summary: String,
    val body: List<String>
)

data class FaithLeader(
    val id: String,
    val name: String,
    val role: String,
    val area: String,
    val rating: Double,
    val reviews: Int,
    val available: Boolean,
    val avatar: String
)

data class ReferralType(
    val id: String,
    val title: String,
    val subtitle: String,
    val tone: String
)

data class AmaniMessage(
    val id: String,
    val sender: String, // "user" or "amani"
    val text: String,
    val time: String,
    val isHelpfulTip: Boolean = false
)
