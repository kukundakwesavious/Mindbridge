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

    suspend fun initializeDefaultData() {
        val currentSession = dao.getSession()
        if (currentSession == null) {
            val defaultSession = SessionEntity(
                anonymousId = "UG-4821",
                displayName = "Alex",
                email = "alex.uganda@gmail.com",
                district = "Kampala",
                university = "Makerere University",
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
                    text = "Took a 10-minute walk through campus today when coursework was overwhelming. Remember to take it one hour at a time!",
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

    suspend fun loginAnonymous(name: String) {
        val randomId = "UG-${(1000..9999).random()}"
        val entity = SessionEntity(
            anonymousId = randomId,
            displayName = if (name.isBlank()) "Anonymous Friend" else name.trim(),
            email = null,
            district = "Kampala",
            university = "Makerere University",
            language = "English",
            notifications = true,
            onboardingComplete = true
        )
        dao.insertSession(entity)
    }

    suspend fun loginWithCredentials(name: String, email: String, district: String, university: String) {
        val randomId = "UG-${(1000..9999).random()}"
        val entity = SessionEntity(
            anonymousId = randomId,
            displayName = if (name.isBlank()) "Friend" else name.trim(),
            email = email.ifBlank { null },
            district = district.ifBlank { "Kampala" },
            university = university.ifBlank { "Makerere University" },
            language = "English",
            notifications = true,
            onboardingComplete = true
        )
        dao.insertSession(entity)
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
            author = if (author.isBlank()) "Anonymous Peer" else author,
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
        val counsellorName = counsellors.find { it.id == counsellorId }?.name ?: "Counsellor"
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

        // Generate intelligent, structured Gemini-style response
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
        val lower = prompt.lowercase(Locale.ROOT)
        return when {
            lower.contains("stress") || lower.contains("anxious") || lower.contains("anxiety") || lower.contains("overwhelm") -> {
                "### Grounding & Calming with Amani\n\n" +
                "It is completely valid to feel overwhelmed right now. Your nervous system is signaling high tension, but you are safe in this moment.\n\n" +
                "### 1. The 5-4-3-2-1 Sensory Grounding Technique\n" +
                "* **5 things you can see** around you (your screen, a tree, your shoes)\n" +
                "* **4 things you can physically touch** (the table, your clothes)\n" +
                "* **3 things you can hear** (the wind, distant voices)\n" +
                "* **2 things you can smell**\n" +
                "* **1 deep, conscious breath**\n\n" +
                "Would you like me to walk you through another calming exercise or connect you with a therapist?"
            }
            lower.contains("breath") || lower.contains("calm") -> {
                "### 2-Minute Box Breathing Exercise 🌿\n\n" +
                "Let's do this together right now. Sit comfortably:\n\n" +
                "1. **Inhale slowly** through your nose: *1... 2... 3... 4...*\n" +
                "2. **Hold your breath gently**: *1... 2... 3... 4...*\n" +
                "3. **Exhale smoothly** through your mouth: *1... 2... 3... 4...*\n" +
                "4. **Pause in stillness**: *1... 2... 3... 4...*\n\n" +
                "Notice your shoulders dropping away from your ears. How does your body feel now?"
            }
            lower.contains("exam") || lower.contains("study") || lower.contains("university") || lower.contains("campus") -> {
                "### Academic & University Coping Strategy\n\n" +
                "Academic pressure at university can feel heavy with deadlines and expectations.\n\n" +
                "### Practical Steps:\n" +
                "* **Pomodoro Technique**: 25 minutes of single-task focus, then 5 minutes of rest.\n" +
                "* **Brain Dump**: Write down all tasks on paper so they don't crowd your thoughts.\n" +
                "* **Prioritize**: Pick the top 2 things to finish today. Everything else can wait.\n\n" +
                "Remember: your exams do not define your human worth."
            }
            lower.contains("suicide") || lower.contains("kill") || lower.contains("harm") || lower.contains("die") -> {
                "### Urgent Support for You\n\n" +
                "Please know that you are deeply valued and not alone. Immediate compassionate human support is available for you right now:\n\n" +
                "• **Uganda Youth & Child Helpline (Sauti)**: 116 (Toll-Free, 24/7)\n" +
                "• **Butabika Hospital Mental Health Helpline**: +256 800 200 600\n" +
                "• **Police/Emergency Ambulance**: 112 or 999\n\n" +
                "Please tap the Crisis button or reach out to someone you trust immediately."
            }
            else -> {
                "### Oli otya! I hear you.\n\n" +
                "Thank you for sharing that with me. Whatever you are navigating today, take a gentle, deep breath.\n\n" +
                "### Next Steps We Can Take:\n" +
                "* **Talk Through It**: Tell me more about what is on your mind.\n" +
                "* **Relaxation**: We can do a quick 2-minute breathing exercise.\n" +
                "* **Find a Specialist**: You can book a confidential session with a verified human counsellor in the **Counsellors** tab."
            }
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
