package com.example.mindbridge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_sessions")
data class SessionEntity(
    @PrimaryKey val anonymousId: String,
    val displayName: String,
    val email: String?,
    val district: String,
    val university: String,
    val language: String,
    val notifications: Boolean,
    val onboardingComplete: Boolean
)

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey val id: String,
    val counsellorId: String,
    val counsellorName: String,
    val mode: String,
    val date: String,
    val time: String,
    val status: String,
    val createdAt: String
)

@Entity(tableName = "peer_posts")
data class PeerPostEntity(
    @PrimaryKey val id: String,
    val author: String,
    val text: String,
    val createdAt: String,
    val likes: Int
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val counsellorId: String,
    val sender: String,
    val text: String,
    val time: String
)

@Entity(tableName = "amani_messages")
data class AmaniMessageEntity(
    @PrimaryKey val id: String,
    val sender: String,
    val text: String,
    val time: String,
    val isHelpfulTip: Boolean
)

@Entity(tableName = "referrals")
data class ReferralEntity(
    @PrimaryKey val id: String,
    val type: String,
    val provider: String,
    val status: String,
    val createdAt: String
)
