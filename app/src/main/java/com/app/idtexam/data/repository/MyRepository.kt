package com.app.idtexam.data.repository

import android.content.Context
import com.app.idtexam.data.model.MockResponseDto
import com.app.idtexam.network.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    var useLocalData: Boolean = false
    suspend fun getStates(): List<MockResponseDto> {
        return if (useLocalData) {
            val jsonString = context.assets.open("mock_response.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val userType = object : TypeToken<List<MockResponseDto>>() {}.type
            gson.fromJson(jsonString, userType)
        } else {
            apiService.getStates()
        }
    }
}