package com.example.coroutinessample.repository

import com.example.coroutinessample.room.database.Database
import com.example.coroutinessample.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository {
    private val database by lazy { Database.instance }

    fun getAllUser(): Flow<List<UserEntity>> = database.userDao().getAllUser()

    fun insert(userEntity: UserEntity) {
        database.userDao().insert(userEntity)
    }

    fun delete(userEntity: UserEntity){
        database.userDao().delete(userEntity)
    }

    fun addToGroup(isGroup:Boolean,id:Long){
        database.userDao().addToGroup(isGroup,id)
    }
}