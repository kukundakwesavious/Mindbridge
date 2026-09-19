package com.example.mindbridge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mindbridge.data.model.*
import com.example.mindbridge.data.repository.MindBridgeRepository
import com.example.mindbridge.ui.navigation.MainTab
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MindBridgeViewModel(
    private val repository: MindBridgeRepository
) : ViewModel() {

    val session: StateFlow<UserSession?> = repository.sessionFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val bookings: StateFlow<List<Booking>> = repository.bookingsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val peerPosts: StateFlow<List<PeerPost>> = repository.peerPostsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val amaniMessages: StateFlow<List<AmaniMessage>> = repository.amaniMessagesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val referrals: StateFlow<List<ReferralRequest>> = repository.referralsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val counsellors: List<Counsellor> = repository.counsellors
    val contentList: List<ContentItem> = repository.contentList
    val referralTypes: List<ReferralType> = repository.referralTypes
    val faithLeaders: List<FaithLeader> = repository.faithLeaders
    val supportedLanguages: List<String> = repository.supportedLanguages

    private val _selectedTab = MutableStateFlow(MainTab.Home)
    val selectedTab: StateFlow<MainTab> = _selectedTab.asStateFlow()

    private val _isSendingAmani = MutableStateFlow(false)
    val isSendingAmani: StateFlow<Boolean> = _isSendingAmani.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeDefaultData()
        }
    }

    fun selectTab(tab: MainTab) {
        _selectedTab.value = tab
    }

    fun loginAnonymous(name: String, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.loginAnonymous(name)
            onDone()
        }
    }

    fun loginWithCredentials(name: String, email: String, district: String, university: String, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.loginWithCredentials(name, email, district, university)
            onDone()
        }
    }

    fun updateLanguage(lang: String) {
        viewModelScope.launch {
            repository.updateLanguage(lang)
        }
    }

    fun toggleNotifications(enabled: Boolean) {
        viewModelScope.launch {
            repository.toggleNotifications(enabled)
        }
    }

    fun logout(onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.logout()
            onDone()
        }
    }

    fun bookSession(counsellorId: String, mode: String, date: String, time: String, onDone: () -> Unit) {
        viewModelScope.launch {
            val counsellor = counsellors.find { it.id == counsellorId }
            val booking = Booking(
                id = "b-${System.currentTimeMillis()}",
                counsellorId = counsellorId,
                counsellorName = counsellor?.name ?: "Verified Counsellor",
                mode = mode,
                date = date,
                time = time,
                status = "Confirmed",
                createdAt = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault()).format(Date())
            )
            repository.addBooking(booking)
            onDone()
        }
    }

    fun addPeerPost(text: String, author: String) {
        viewModelScope.launch {
            repository.addPeerPost(text, author)
        }
    }

    fun sendAmaniMessage(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            _isSendingAmani.value = true
            try {
                repository.sendAmaniMessage(text)
            } finally {
                _isSendingAmani.value = false
            }
        }
    }

    fun getChatMessages(counsellorId: String): Flow<List<ChatMessage>> {
        return repository.getChatMessagesFlow(counsellorId)
    }

    fun sendChatMessage(counsellorId: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.sendChatMessage(counsellorId, text)
        }
    }

    fun requestReferral(type: String, provider: String, onDone: () -> Unit) {
        viewModelScope.launch {
            repository.requestReferral(type, provider)
            onDone()
        }
    }

    class Factory(private val repository: MindBridgeRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MindBridgeViewModel::class.java)) {
                return MindBridgeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
