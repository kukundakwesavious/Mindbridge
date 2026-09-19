package com.example.mindbridge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        SessionEntity::class,
        BookingEntity::class,
        PeerPostEntity::class,
        ChatMessageEntity::class,
        AmaniMessageEntity::class,
        ReferralEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MindBridgeDatabase : RoomDatabase() {
    abstract fun mindBridgeDao(): MindBridgeDao

    companion object {
        @Volatile
        private var INSTANCE: MindBridgeDatabase? = null

        fun getDatabase(context: Context): MindBridgeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MindBridgeDatabase::class.java,
                    "mindbridge_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
