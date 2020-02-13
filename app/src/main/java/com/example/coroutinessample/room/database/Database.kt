package com.example.coroutinessample.room.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object Database {

     lateinit var instance: AppDatabase

    private fun create(context: Context, @Suppress("SameParameterValue") memoryOnly: Boolean): AppDatabase {
        val b: RoomDatabase.Builder<AppDatabase> =
            Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java,
                DB_NAME
            )
        return b.build()
    }

    private const val DB_NAME: String = "test.db"
    @Synchronized
    fun init(context: Context) {
        instance = create(context, false)
    }

}