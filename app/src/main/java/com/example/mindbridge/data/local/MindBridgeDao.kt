package com.example.mindbridge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MindBridgeDao {
    // Session
    @Query("SELECT * FROM user_sessions LIMIT 1")
    fun getSessionFlow(): Flow<SessionEntity?>

    @Query("SELECT * FROM user_sessions LIMIT 1")
    suspend fun getSession(): SessionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity)

    @Query("DELETE FROM user_sessions")
    suspend fun clearSession()

    // Bookings
    @Query("SELECT * FROM bookings ORDER BY createdAt DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity)

    // Peer posts
    @Query("SELECT * FROM peer_posts ORDER BY createdAt DESC")
    fun getAllPeerPosts(): Flow<List<PeerPostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeerPost(post: PeerPostEntity)

    // Chat messages
    @Query("SELECT * FROM chat_messages WHERE counsellorId = :counsellorId ORDER BY time ASC")
    fun getChatMessages(counsellorId: String): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessageEntity)

    // Amani AI Messages
    @Query("SELECT * FROM amani_messages ORDER BY time ASC")
    fun getAmaniMessages(): Flow<List<AmaniMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmaniMessage(message: AmaniMessageEntity)

    @Query("DELETE FROM amani_messages")
    suspend fun clearAmaniMessages()

    // Referrals
    @Query("SELECT * FROM referrals ORDER BY createdAt DESC")
    fun getAllReferrals(): Flow<List<ReferralEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferral(referral: ReferralEntity)
}
