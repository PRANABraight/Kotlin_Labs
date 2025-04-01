package com.example.a10lab.model

data class SavedLocation(
    val id: Int = 0,
    val cityName: String,
    val lastTemp: Double = 0.0,
    val lastUpdated: Long = System.currentTimeMillis()
)
