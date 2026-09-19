package com.example.mindbridge

import android.app.Application
import com.example.mindbridge.data.local.MindBridgeDatabase
import com.example.mindbridge.data.repository.MindBridgeRepository

class MindBridgeApplication : Application() {
    val database: MindBridgeDatabase by lazy {
        MindBridgeDatabase.getDatabase(this)
    }

    val repository: MindBridgeRepository by lazy {
        MindBridgeRepository(database.mindBridgeDao())
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MindBridgeApplication
            private set
    }
}
