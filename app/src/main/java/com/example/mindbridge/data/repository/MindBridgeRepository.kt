package com.example.mindbridge.data.repository

import com.example.mindbridge.data.local.*
import com.example.mindbridge.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MindBridgeRepository(private val dao: MindBridgeDao) {

    val sessionFlow: Flow<UserSession?> = dao.getSessionFlow().map { entity ->
        entity?.let {
            UserSession(
                anonymousId = it.anonymousId,
                displayName = it.displayName,
                email = it.email,
                district = it.district,
                university = it.university,
                accountType = it.accountType,
                language = it.language,
                notifications = it.notifications,
                onboardingComplete = it.onboardingComplete
            )
        }
    }

    val bookingsFlow: Flow<List<Booking>> = dao.getAllBookings().map { list ->
        list.map {
            Booking(
                id = it.id,
                counsellorId = it.counsellorId,
                counsellorName = it.counsellorName,
                mode = it.mode,
                date = it.date,
                time = it.time,
                status = it.status,
                createdAt = it.createdAt
            )
        }
    }

    val peerPostsFlow: Flow<List<PeerPost>> = dao.getAllPeerPosts().map { list ->
        list.map {
            PeerPost(
                id = it.id,
                author = it.author,
                text = it.text,
                createdAt = it.createdAt,
                likes = it.likes
            )
        }
    }

    val amaniMessagesFlow: Flow<List<AmaniMessage>> = dao.getAmaniMessages().map { list ->
        list.map {
            AmaniMessage(
                id = it.id,
                sender = it.sender,
                text = it.text,
                time = it.time,
                isHelpfulTip = it.isHelpfulTip
            )
        }
    }

    val referralsFlow: Flow<List<ReferralRequest>> = dao.getAllReferrals().map { list ->
        list.map {
            ReferralRequest(
                id = it.id,
                type = it.type,
                provider = it.provider,
                status = it.status,
                createdAt = it.createdAt
            )
        }
    }

    // Static collections preserving the complete domain dataset
    val counsellors: List<Counsellor> = listOf(
        Counsellor(
            id = "c1",
            name = "Dr. Grace Nakunda",
            role = "Psychologist",
            languages = listOf("English", "Luganda"),
            speciality = "Youth wellbeing & academic pressure",
            rating = 4.8,
            reviews = 124,
            available = true,
            avatarInitials = "GN",
            imageDrawableName = "avatar_grace"
        ),
        Counsellor(
            id = "c2",
            name = "Mr. Samuel Kato",
            role = "Counsellor",
            languages = listOf("Luganda", "Runyankore"),
            speciality = "Stress & relationships",
            rating = 4.7,
            reviews = 98,
            available = true,
            avatarInitials = "SK",
            imageDrawableName = "avatar_samuel"
        ),
        Counsellor(
            id = "c3",
            name = "Ms. Patricia Akampurira",
            role = "Clinical Psychologist",
            languages = listOf("English", "Luganda"),
            speciality = "Anxiety & depression management",
            rating = 4.9,
            reviews = 86,
            available = true,
            avatarInitials = "PA",
            imageDrawableName = "avatar_patricia"
        ),
        Counsellor(
            id = "c4",
            name = "Dr. Ronald Ssegorere",
            role = "Counsellor",
            languages = listOf("English", "Runyankore"),
            speciality = "HIV psychosocial support",
            rating = 4.6,
            reviews = 71,
            available = false,
            avatarInitials = "RS",
            imageDrawableName = "avatar_ronald"
        )
    )

    val contentList: List<ContentItem> = listOf(
        ContentItem(
            id = "p1",
            title = "Understanding Depression",
            type = "Article",
            time = "5 min read",
            category = "Mental Wellbeing",
            tone = "blue",
            summary = "Learn what depression can look like, when to seek support, and small steps that may help.",
            body = listOf(
                "Depression can affect mood, energy, sleep, concentration and everyday activities.",
                "You do not have to manage difficult feelings alone. Talking to a trusted person or qualified professional can be a useful next step.",
                "If you feel unsafe or at immediate risk, use the emergency support option in MindBridge."
            )
        ),
        ContentItem(
            id = "p2",
            title = "Managing Anxiety",
            type = "Guide",
            time = "8 min",
            category = "Mental Wellbeing",
            tone = "green",
            summary = "Simple grounding, breathing and self-care practices for stressful moments.",
            body = listOf(
                "Pause and notice what you can see, hear and feel around you.",
                "Try slow, comfortable breathing and reduce immediate distractions where possible.",
                "If anxiety is persistent or interfering with daily life, consider speaking with a counsellor."
            )
        ),
        ContentItem(
            id = "p3",
            title = "Living Positively with HIV",
            type = "Article",
            time = "6 min read",
            category = "HIV/AIDS",
            tone = "pink",
            summary = "Psychosocial support for coping, connection, stigma and wellbeing.",
            body = listOf(
                "Living with HIV can involve emotional and social challenges as well as health care needs.",
                "Confidential support and trusted relationships can help people cope with stigma and isolation.",
                "MindBridge does not require HIV disclosure to access general mental health features."
            )
        ),
        ContentItem(
            id = "p4",
            title = "Stress Management Tips",
            type = "Guide",
            time = "7 min",
            category = "Mental Wellbeing",
            tone = "orange",
            summary = "Everyday ways to recognise stress and build healthier coping routines.",
            body = listOf(
                "Identify situations that repeatedly increase stress.",
                "Break large tasks into smaller steps and create time for rest and supportive relationships.",
                "Reach out for professional support when stress becomes difficult to manage."
            )
        ),
        ContentItem(
            id = "p5",
            title = "Building Healthy Relationships",
            type = "Article",
            time = "5 min read",
            category = "Relationships",
            tone = "teal",
            summary = "Communication, boundaries, trust and knowing when to ask for support.",
            body = listOf(
                "Healthy relationships involve respect, communication and personal boundaries.",
                "Ask for clarification rather than assuming what another person means.",
                "Support is available when a relationship becomes harmful or overwhelming."
            )
        )
    )

    val referralTypes: List<ReferralType> = listOf(
        ReferralType("community", "Community Leader", "Trusted local community support", "green"),
        ReferralType("faith", "Faith Leader", "Spiritual and emotional support", "gold"),
        ReferralType("facility", "Health Facility", "Referral to professional medical care", "blue"),
        ReferralType("emergency", "Emergency Help", "For urgent situations & helplines", "red")
    )

    val faithLeaders: List<FaithLeader> = listOf(
        FaithLeader("f1", "Pastor John Musinguzi", "Faith Leader", "Kigezi Region", 4.6, 52, true, "JM")
    )

    val supportedLanguages = listOf("English", "Luganda", "Runyankore", "Swahili")

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

    suspend fun initializeDefaultData() {
        val currentSession = dao.getSession()
        if (currentSession == null) {
            val defaultSession = SessionEntity(
                anonymousId = "UG-4821",
                displayName = "Alex",
                email = "alex.uganda@gmail.com",
                district = "Mbarara",
                university = "Mbarara University of Science and Technology (MUST)",
                accountType = "Student",
                language = "English",
                notifications = true,
                onboardingComplete = true
            )
            dao.insertSession(defaultSession)

            // Seed initial Amani welcome
            dao.insertAmaniMessage(
                AmaniMessageEntity(
                    id = "msg-amani-welcome",
                    sender = "amani",
                    text = "Oli otya! I'm Amani, your 24/7 MindBridge AI companion. 'Amani' means Peace—and that is exactly what I am here to bring you.\n\nWhether you need practical guidance, help with anxiety, academic pressure, self-care exercises, or finding a human counsellor—I am right here with you.\n\nHow are you feeling right now?",
                    time = getCurrentTimeString(),
                    isHelpfulTip = true
                )
            )

            // Seed sample peer post
            dao.insertPeerPost(
                PeerPostEntity(
                    id = "post-1",
                    author = "Anonymous Peer",
                    text = "Took a 10-minute walk through MUST campus today. Coursework was overwhelming, but the fresh air helped. Remember to take it one hour at a time!",
                    createdAt = "Today, 10:30 AM",
                    likes = 14
                )
            )

            // Seed sample booking
            dao.insertBooking(
                BookingEntity(
                    id = "b-init-1",
                    counsellorId = "c1",
                    counsellorName = "Dr. Grace Nakunda",
                    mode = "Text chat",
                    date = "Tomorrow",
                    time = "2:00 PM",
                    status = "Confirmed",
                    createdAt = getCurrentTimeString()
                )
            )
        }
    }

    suspend fun loginWithCredentials(name: String, email: String, district: String, university: String, accountType: String) {
        val randomId = "UG-${(1000..9999).random()}"
        val entity = SessionEntity(
            anonymousId = randomId,
            displayName = if (name.isBlank()) "Friend" else name.trim(),
            email = email.ifBlank { null },
            district = district.ifBlank { "Mbarara" },
            university = university.ifBlank { "MUST" },
            accountType = accountType,
            language = "English",
            notifications = true,
            onboardingComplete = true
        )
        dao.insertSession(entity)
    }

    suspend fun updateProfile(name: String, email: String?, district: String, university: String, accountType: String) {
        val session = dao.getSession() ?: return
        val updated = session.copy(
            displayName = name,
            email = email,
            district = district,
            university = university,
            accountType = accountType
        )
        dao.insertSession(updated)
    }

    suspend fun updateLanguage(lang: String) {
        val session = dao.getSession() ?: return
        dao.insertSession(session.copy(language = lang))
    }

    suspend fun toggleNotifications(enabled: Boolean) {
        val session = dao.getSession() ?: return
        dao.insertSession(session.copy(notifications = enabled))
    }

    suspend fun logout() {
        dao.clearSession()
    }

    suspend fun addBooking(booking: Booking) {
        dao.insertBooking(
            BookingEntity(
                id = booking.id,
                counsellorId = booking.counsellorId,
                counsellorName = booking.counsellorName,
                mode = booking.mode,
                date = booking.date,
                time = booking.time,
                status = booking.status,
                createdAt = booking.createdAt
            )
        )
    }

    suspend fun addPeerPost(text: String, author: String) {
        val post = PeerPostEntity(
            id = "post-${System.currentTimeMillis()}",
            author = "Anonymous Peer", // Enforcing anonymity for all peer group posts
            text = text,
            createdAt = "Just now",
            likes = 0
        )
        dao.insertPeerPost(post)
    }

    fun getChatMessagesFlow(counsellorId: String): Flow<List<ChatMessage>> {
        return dao.getChatMessages(counsellorId).map { list ->
            list.map {
                ChatMessage(
                    id = it.id,
                    counsellorId = it.counsellorId,
                    sender = it.sender,
                    text = it.text,
                    time = it.time
                )
            }
        }
    }

    suspend fun sendChatMessage(counsellorId: String, text: String) {
        val msgTime = getCurrentTimeString()
        val userMsg = ChatMessageEntity(
            id = "msg-${System.currentTimeMillis()}",
            counsellorId = counsellorId,
            sender = "user",
            text = text,
            time = msgTime
        )
        dao.insertChatMessage(userMsg)

        // Automatic supportive reply from counsellor after sending
        val reply = ChatMessageEntity(
            id = "msg-${System.currentTimeMillis() + 1}",
            counsellorId = counsellorId,
            sender = "counsellor",
            text = "Hello! Thank you for reaching out. I have received your message and I am reviewing our session plan. You are in a safe space.",
            time = getCurrentTimeString()
        )
        dao.insertChatMessage(reply)
    }

    suspend fun sendAmaniMessage(userText: String) {
        val timeNow = getCurrentTimeString()
        val userMsg = AmaniMessageEntity(
            id = "user-${System.currentTimeMillis()}",
            sender = "user",
            text = userText,
            time = timeNow,
            isHelpfulTip = false
        )
        dao.insertAmaniMessage(userMsg)

        val replyText = generateAmaniResponse(userText)
        val amaniReply = AmaniMessageEntity(
            id = "amani-${System.currentTimeMillis() + 1}",
            sender = "amani",
            text = replyText,
            time = getCurrentTimeString(),
            isHelpfulTip = false
        )
        dao.insertAmaniMessage(amaniReply)
    }

    private fun generateAmaniResponse(prompt: String): String {
        val lower = prompt.lowercase(Locale.ROOT).trim()
        return when {
            lower.contains("oli otya") || lower.contains("agandi") || lower.startsWith("hi") || lower.startsWith("hello") -> {
                "### Agandi! Oli otya! 🌸\n\nI am **Amani**, your MindBridge companion. How are you feeling today?"
            }
            lower.contains("suicide") || lower.contains("kill myself") || lower.contains("harm") -> {
                "### You are not alone ❤️\n\nPlease reach out for immediate help:\n• Toll-free: **116**\n• Butabika: **0800 200 600**"
            }
            else -> "### I am here for you 🤝\n\nTell me more about what is on your mind."
        }
    }

    suspend fun requestReferral(type: String, provider: String) {
        val referral = ReferralEntity(
            id = "ref-${System.currentTimeMillis()}",
            type = type,
            provider = provider,
            status = "Pending review",
            createdAt = getCurrentTimeString()
        )
        dao.insertReferral(referral)
    }

    private fun getCurrentTimeString(): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date())
    }
}
