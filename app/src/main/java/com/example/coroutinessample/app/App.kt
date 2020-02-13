package com.example.coroutinessample.app

import android.app.Application
import com.example.coroutinessample.room.database.AppDatabase
import com.example.coroutinessample.room.database.Database

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}