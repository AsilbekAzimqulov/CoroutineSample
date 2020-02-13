package com.example.coroutinessample.interactor

import com.example.coroutinessample.repository.UserRepository
import com.example.coroutinessample.room.entity.UserEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UserInteractor {
    private val repository by lazy { UserRepository() }


    fun getAllUser() = repository.getAllUser()

    fun getGroupUser() = flow {
        //query orqali qilsamham bo'lardi buyoqda filter qimasdan ,
        //lekin interactor faqat olib o'tuvchi bo'lib foydasiz bo'lib qolyapti
        repository.getAllUser().collect {
            emit(it.filter { it.isGroup }.sortedByDescending { it.id })
        }
    }

    fun getUnGroupUser() = flow {
        repository.getAllUser().collect {
            emit(it.filter { !it.isGroup }.sortedByDescending { it.id })
        }
    }

    fun addUser(name: String, imageUrl: String) {
        val user = UserEntity(name = name, imgUrl = imageUrl, isGroup = false)
        repository.insert(user)
    }

    fun deleteUser(userEntity: UserEntity) {
        repository.delete(userEntity)
    }

    fun addToGroup(userEntity: UserEntity){
        repository.addToGroup(true,userEntity.id)
    }
}