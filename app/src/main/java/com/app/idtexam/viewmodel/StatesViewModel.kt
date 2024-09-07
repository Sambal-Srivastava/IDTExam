package com.app.idtexam.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.idtexam.data.model.MockResponseDto
import com.app.idtexam.data.repository.MyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatesViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<MockResponseDto>>()
    val users: LiveData<List<MockResponseDto>> = _users

    fun fetchUsers(useLocalData: Boolean) {
        myRepository.useLocalData = useLocalData
        viewModelScope.launch {
            try {
                _users.value = myRepository.getStates()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}