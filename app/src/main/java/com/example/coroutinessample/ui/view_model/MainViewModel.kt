package com.example.coroutinessample.ui.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinessample.interactor.UserInteractor
import com.example.coroutinessample.room.entity.UserEntity
import com.example.coroutinessample.utils.CustomLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val interactor by lazy { UserInteractor() }
    private val _allData = CustomLiveData<List<UserEntity>>()
    val allData = _allData
    private val _groupData = CustomLiveData<List<UserEntity>>()
    val groupData = _groupData
    private val _unGroupData = CustomLiveData<List<UserEntity>>()
    val unGroupData = _unGroupData

    val scrollPager = CustomLiveData<Boolean>()
    val clearInfoInUI = CustomLiveData<Boolean>()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getAllUser().collect {
                Log.d("MRX", it.size.toString())
                _allData.postValue(it)
            }
        }
    }

    fun loadGroupData() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getGroupUser().collect {
                _groupData.postValue(it)
            }
        }
    }

    fun loadUnGroupData() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getUnGroupUser().collect {
                _unGroupData.postValue(it)
            }
        }
    }

    fun delete(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.deleteUser(userEntity)
        }
    }

    fun addData(name: String, imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.addUser(name, imageUrl)
        }
    }

    fun addToGroup(userEntity: UserEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.addToGroup(userEntity)
        }
    }

    fun scrollPager() {
        scrollPager.call()
    }

    fun clearUI() {
        clearInfoInUI.call()
    }
}