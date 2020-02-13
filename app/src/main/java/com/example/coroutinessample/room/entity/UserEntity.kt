package com.example.coroutinessample.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val name:String,
    val imgUrl:String,
    val isGroup:Boolean
)