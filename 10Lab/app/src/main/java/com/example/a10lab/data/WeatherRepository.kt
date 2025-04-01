package com.example.a10lab.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

class WeatherRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    private val apiKey = "28028eb26a162a3876d89bfe01a6b28a"

    suspend fun getWeatherByCity(city: String) = withContext(Dispatchers.IO) {
        try {
            api.getWeatherByCity(city, apiKey)
        } catch (e: UnknownHostException) {
            throw Exception("Unable to connect to server. Please check your internet connection.")
        } catch (e: Exception) {
            throw Exception("Error fetching weather data: ${e.message}")
        }
    }

    suspend fun getWeatherByCoordinates(latitude: Double, longitude: Double) = withContext(Dispatchers.IO) {
        try {
            api.getWeatherByCoordinates(latitude, longitude, apiKey)
        } catch (e: UnknownHostException) {
            throw Exception("Unable to connect to server. Please check your internet connection.")
        } catch (e: Exception) {
            throw Exception("Error fetching weather data: ${e.message}")
        }
    }
}

