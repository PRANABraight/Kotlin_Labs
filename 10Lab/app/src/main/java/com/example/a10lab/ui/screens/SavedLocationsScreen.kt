package com.example.a10lab.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.a10lab.data.LocationDatabase
import com.example.a10lab.data.WeatherRepository
import com.example.a10lab.ui.theme.AccentOrange
import com.example.a10lab.ui.theme.CardGradientEnd
import com.example.a10lab.ui.theme.CardGradientStart
import com.example.a10lab.ui.theme.HotPink
import com.example.a10lab.ui.theme.PrimaryBlue
import com.example.a10lab.ui.theme.SurfaceLight
import com.example.a10lab.ui.theme.TextPrimary
import com.example.a10lab.ui.theme.TextSecondary
import com.example.a10lab.ui.theme.TextWhite
import com.example.a10lab.ui.theme.VibrantPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedLocationsScreen(
    repository: WeatherRepository,
    onLocationClick: (String) -> Unit
) {
    var newCity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(CardGradientStart, CardGradientEnd)
                )
            )
    ) {
        // Add new location section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newCity,
                    onValueChange = { newCity = it },
                    label = { Text("Add new location") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryBlue,
                        focusedLabelColor = PrimaryBlue
                    )
                )
                FloatingActionButton(
                    onClick = {
                        if (newCity.isNotBlank()) {
                            LocationDatabase.addLocation(newCity)
                            newCity = ""
                        }
                    },
                    modifier = Modifier.padding(start = 8.dp),
                    containerColor = VibrantPurple
                ) {
                    Icon(Icons.Default.Add, "Add location", tint = TextWhite)
                }
            }
        }

        // List of saved locations
        LazyColumn {
            items(LocationDatabase.getAllLocations()) { location ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateContentSize(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    onClick = { onLocationClick(location.cityName) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = location.cityName,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary
                            )
                            if (location.lastTemp != 0.0) {
                                Text(
                                    text = "${location.lastTemp}°C",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = HotPink
                                )
                            }
                        }
                        IconButton(
                            onClick = { LocationDatabase.removeLocation(location) }
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                "Remove location",
                                tint = TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}
