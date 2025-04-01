package com.example.a10lab.data

import androidx.compose.runtime.mutableStateListOf
import com.example.a10lab.model.SavedLocation

object LocationDatabase {
    private val locations = mutableStateListOf<SavedLocation>()
    
    fun addLocation(cityName: String) {
        if (!locations.any { it.cityName.equals(cityName, ignoreCase = true) }) {
            locations.add(SavedLocation(locations.size, cityName))
        }
    }
    
    fun removeLocation(location: SavedLocation) {
        locations.remove(location)
    }
    
    fun getAllLocations() = locations.toList()
    
    fun updateLocationWeather(cityName: String, temp: Double) {
        val index = locations.indexOfFirst { it.cityName.equals(cityName, ignoreCase = true) }
        if (index != -1) {
            locations[index] = locations[index].copy(lastTemp = temp, lastUpdated = System.currentTimeMillis())
        }
    }
}
