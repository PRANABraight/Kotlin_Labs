package com.example.a10lab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.a10lab.data.LocationDatabase
import com.example.a10lab.data.WeatherRepository
import com.example.a10lab.model.WeatherResponse
import com.example.a10lab.ui.components.WeatherCard
import com.example.a10lab.ui.screens.SavedLocationsScreen
import com.example.a10lab.ui.theme.DeepBlue
import com.example.a10lab.ui.theme.TextWhite
import com.example.a10lab.ui.theme._10LabTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val weatherRepository = WeatherRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _10LabTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen(weatherRepository, lifecycleScope)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(repository: WeatherRepository, lifecycleScope: CoroutineScope) {
    var weatherData by remember { mutableStateOf<WeatherResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var city by remember { mutableStateOf("") }
    var showingSavedLocations by remember { mutableStateOf(false) }

    // Fetch Darjeeling weather on initial launch
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.Main) {
            try {
                isLoading = true
                weatherData = repository.getWeatherByCity("Darjeeling")
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "WhatZaWeather",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    ) 
                },
                actions = {
                    IconButton(onClick = { showingSavedLocations = !showingSavedLocations }) {
                        Icon(
                            Icons.Default.List,
                            "Saved Locations",
                            tint = Color.White

                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepBlue
                )
            )
        },
        bottomBar = {
            BottomAppBar {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Search other cities") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                IconButton(
                    onClick = {
                        if (city.isNotBlank()) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                try {
                                    isLoading = true
                                    error = null
                                    weatherData = repository.getWeatherByCity(city)
                                    LocationDatabase.addLocation(city)
                                } catch (e: Exception) {
                                    error = e.message
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Search"
                    )
                }
            }
        }
    ) { paddingValues ->
        if (showingSavedLocations) {
            SavedLocationsScreen(
                repository = repository,
                onLocationClick = { selectedCity ->
                    city = selectedCity
                    showingSavedLocations = false
                    // Trigger weather fetch for selected city
                    lifecycleScope.launch(Dispatchers.Main) {
                        try {
                            isLoading = true
                            error = null
                            weatherData = repository.getWeatherByCity(selectedCity)
                            LocationDatabase.addLocation(selectedCity)
                            LocationDatabase.updateLocationWeather(selectedCity, weatherData?.main?.temp ?: 0.0)
                        } catch (e: Exception) {
                            error = e.message
                        } finally {
                            isLoading = false
                        }
                    }
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp),
                        color = DeepBlue
                    )
                }

                error?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFF6B6B)
                        )
                    ) {
                        Text(
                            text = it,
                            color = TextWhite,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                weatherData?.let {
                    WeatherCard(weather = it)
                }
            }
        }
    }
}