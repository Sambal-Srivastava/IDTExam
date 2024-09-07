package com.app.idtexam.network

import com.app.idtexam.data.model.MockResponseDto
import retrofit2.http.GET


interface ApiService {
    /*@GET("data/2.5/weather?")
    suspend fun getWeatherData(
        @Query("q") q: String,
        @Query("appid") appid: String
    ): Response<WeatherDto.Response>

    @GET("data/2.5/weather?")
    suspend fun getWeatherDataCurrentLoc(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String
    ): Response<WeatherDto.Response>*/
    @GET("users")
    suspend fun getStates(): List<MockResponseDto>
}