package com.example.coroutinessample.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.coroutinessample.room.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {
    @Query("select * from user_table order by id DESC")
    abstract fun getAllUser(): Flow<List<UserEntity>>

    @Query("select * from user_table where isGroup =:isGroup")
    abstract fun getAllGroupUser(isGroup: Boolean = true): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(userEntity: UserEntity)

    @Delete
    abstract fun delete(userEntity: UserEntity)

    @Query("update user_table set  isGroup=:isGroup where id=:id")
    abstract fun addToGroup(isGroup: Boolean, id: Long)
}