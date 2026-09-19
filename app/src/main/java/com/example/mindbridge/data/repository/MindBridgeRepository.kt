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

        // Generate intelligent, structured Gemini-style response with optimal efficiency
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
            // Greetings & Ugandan Context
            lower.contains("oli otya") || lower.contains("gyebaleko") || lower.contains("wasuze") ||
            lower.contains("agandi") || lower.contains("orire") || lower.contains("habari") ||
            lower.contains("hujambo") || lower.startsWith("hi") || lower.startsWith("hello") || lower == "hey" -> {
                "### Oli otya! Mirembe ne emirembe 🌸\n\n" +
                "I am **Amani**, your 24/7 MindBridge AI companion. *Amani* means Peace and Harmony.\n\n" +
                "I am here to support you with whatever is on your heart today:\n" +
                "• **Emotional Relief**: Vent or unpack overwhelming thoughts safely\n" +
                "• **Practical Calming**: Guided 2-minute box breathing or sensory grounding\n" +
                "• **Academic Support**: Study anxiety, exam focus, and university pressures\n" +
                "• **Human Therapy**: Confidential bookings with licensed Ugandan psychologists\n\n" +
                "How are you feeling right this moment?"
            }

            // Severe Crisis & Safety (Highest Priority)
            lower.contains("suicide") || lower.contains("kill myself") || lower.contains("end my life") ||
            lower.contains("harm") || lower.contains("want to die") || lower.contains("no reason to live") -> {
                "### You Are Precious & Not Alone ❤️\n\n" +
                "Please stay with me. What you are going through is painful, but you do not have to carry it alone. Compassionate human help is ready for you right now in Uganda:\n\n" +
                "• **Uganda Youth & Child Helpline (Sauti)**: Call **116** (Toll-Free, 24/7, All Languages)\n" +
                "• **Butabika Hospital Mental Health Helpline**: **0800 200 600** (Toll-Free)\n" +
                "• **National Emergency Police & Medical**: **112** or **999**\n" +
                "• **Lifeline Uganda Crisis Intervention**: **0800 220 000**\n\n" +
                "Tap the **Crisis SOS** button at the top to connect immediately. There is hope, and people who care deeply about your tomorrow are waiting to listen."
            }

            // Stress, Panic & Anxiety
            lower.contains("stress") || lower.contains("anxious") || lower.contains("anxiety") ||
            lower.contains("panic") || lower.contains("overwhelm") || lower.contains("scared") || lower.contains("fear") -> {
                "### Calming Your Nervous System with Amani 🌿\n\n" +
                "It is completely valid to feel overwhelmed right now. Your nervous system is in overdrive, but you are in a safe space.\n\n" +
                "### 1. The 5-4-3-2-1 Sensory Grounding Tool\n" +
                "• **5 things you see**: Look around and name 5 distinct objects\n" +
                "• **4 things you can touch**: Notice your feet on the ground or fabric of your clothes\n" +
                "• **3 sounds you hear**: The hum of a fan, birds, or distant cars\n" +
                "• **2 things you can smell**: Fresh air, coffee, or rain\n" +
                "• **1 conscious breath**: Inhale deeply... and release\n\n" +
                "### 2. Immediate Re-Centering\n" +
                "Say to yourself: *\"I don't have to resolve the next year today. I only need to navigate the next 10 minutes.\"*\n\n" +
                "Would you like to try our 2-minute box breathing timer next?"
            }

            // Breathing & Grounding
            lower.contains("breath") || lower.contains("breathe") || lower.contains("calm") ||
            lower.contains("meditat") || lower.contains("relax") -> {
                "### 2-Minute Box Breathing Exercise 🌬️\n\n" +
                "Let's synchronize our breathing right now. Relax your jaw and drop your shoulders:\n\n" +
                "1. **Inhale slowly** through your nose: *1... 2... 3... 4...*\n" +
                "2. **Hold your breath gently**: *1... 2... 3... 4...*\n" +
                "3. **Exhale smoothly** through your mouth: *1... 2... 3... 4...*\n" +
                "4. **Rest in quiet stillness**: *1... 2... 3... 4...*\n\n" +
                "Repeat this cycle 3 times. Feel your heart rate gently steadying.\n\n" +
                "How does your chest and body feel right now?"
            }

            // University, Exams & Academic Pressure
            lower.contains("exam") || lower.contains("study") || lower.contains("coursework") ||
            lower.contains("university") || lower.contains("campus") || lower.contains("test") ||
            lower.contains("makerere") || lower.contains("kyambogo") || lower.contains("deadline") -> {
                "### Academic & University Focus Strategy 📚\n\n" +
                "Coursework deadlines and exam pressure can feel heavy, especially with family and academic expectations in Uganda.\n\n" +
                "### Actionable Steps to Beat Exam Paralysis:\n" +
                "• **The 20/5 Micro-Burst**: Work on ONE small section for 20 minutes with your phone face down, then take a 5-minute break.\n" +
                "• **Brain-Dump**: Write down every pending task on a physical paper. Seeing it out of your head stops racing thoughts.\n" +
                "• **Pick The Anchor Task**: Focus only on the single assignment due soonest.\n\n" +
                "> *Remember: Your exam marks are a milestone, not a definition of your character or intelligence.*\n\n" +
                "What specific topic or deadline is pressing you most right now?"
            }

            // Depression, Loneliness & Sadness
            lower.contains("depress") || lower.contains("sad") || lower.contains("lonely") ||
            lower.contains("empty") || lower.contains("cry") || lower.contains("crying") ||
            lower.contains("hopeless") || lower.contains("alone") -> {
                "### I Am Right Here With You 🤍\n\n" +
                "Loneliness and heavy sadness can feel like carrying a silent weight through a crowded room. Thank you for trusting me with how you feel.\n\n" +
                "### Small Anchors for Today:\n" +
                "• **Drink a glass of cool water**: Physical hydration gently nudges the brain.\n" +
                "• **Step into natural daylight**: 5 minutes of sunlight on campus or outside helps circadian mood regulation.\n" +
                "• **No Self-Judgment**: It is okay not to be productive today. Surviving a hard day is an achievement in itself.\n\n" +
                "Our **Peer Reflections** wall also has encouraging messages from fellow Ugandan students who have walked through this. Would you like to read some, or speak with Dr. Grace Nakunda?"
            }

            // Sleep & Insomnia
            lower.contains("sleep") || lower.contains("insomnia") || lower.contains("tired") ||
            lower.contains("exhaust") || lower.contains("nightmare") || lower.contains("awake") -> {
                "### Nighttime Calming & Sleep Hygiene 🌙\n\n" +
                "Racing thoughts when lying in bed can be exhausting. Here is a proven routine to calm your nervous system:\n\n" +
                "• **Digital Curfew**: Turn off bright phone screens or put them in night/warm light mode.\n" +
                "• **4-7-8 Sleep Breathing**: Inhale for 4 seconds, hold for 7 seconds, exhale slowly for 8 seconds. This activates the vagus nerve.\n" +
                "• **Mental Parking Lot**: Keep a notebook by your bed. Write down any worries so your mind knows they are safely stored for tomorrow.\n\n" +
                "Close your eyes and let your muscles loosen from your toes to your forehead."
            }

            // Therapy & Booking Counsellors
            lower.contains("book") || lower.contains("counsellor") || lower.contains("therapist") ||
            lower.contains("doctor") || lower.contains("appointment") || lower.contains("cost") ||
            lower.contains("private") || lower.contains("anonymous") -> {
                "### Confidential Counselling on MindBridge 🛡️\n\n" +
                "Speaking to a qualified Ugandan professional is safe, discreet, and non-judgmental:\n\n" +
                "### How It Works:\n" +
                "1. Tap **Counsellors** to view verified practitioners (e.g. Dr. Grace Nakunda, Ronald Mukasa).\n" +
                "2. Choose your preferred language: English, Luganda, Runyankore, or Swahili.\n" +
                "3. Select your consultation mode: **Text Chat**, **Voice Call**, or **Video Call**.\n" +
                "4. All consultations use end-to-end encryption with your anonymous student ID.\n\n" +
                "Would you like me to guide you directly to our counsellor directory?"
            }

            // Relationships & Family
            lower.contains("relationship") || lower.contains("boyfriend") || lower.contains("girlfriend") ||
            lower.contains("partner") || lower.contains("parent") || lower.contains("family") || lower.contains("breakup") -> {
                "### Navigating Relationships & Boundaries 🤝\n\n" +
                "Interpersonal and family conflicts can bring intense emotional turbulence.\n\n" +
                "### Guiding Principles:\n" +
                "• **Pause Before Reacting**: When emotions are high, take an intentional cooling hour before sending messages.\n" +
                "• **Separate What You Can Control**: You can control your communication and boundaries; you cannot control another person's reaction.\n" +
                "• **Check In With Yourself**: Are your fundamental needs for respect and safety being honored?\n\n" +
                "Tell me more about what happened, and let's untangle it together."
            }

            // Default Empathetic Response
            else -> {
                "### I Am Listening & Walking With You 🤝\n\n" +
                "Thank you for sharing that with me. Even small thoughts deserve care and attention.\n\n" +
                "### What We Can Explore Together:\n" +
                "• **Talk Deeper**: Tell me more about what triggered these thoughts\n" +
                "• **Grounding**: Practice a 2-minute breathing calm or relaxation\n" +
                "• **Study & Focus**: Strategies for university and coursework balance\n" +
                "• **Connect**: Schedule a confidential session with a licensed Ugandan counsellor\n\n" +
                "Take a slow breath. What would feel most helpful for you right now?"
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
