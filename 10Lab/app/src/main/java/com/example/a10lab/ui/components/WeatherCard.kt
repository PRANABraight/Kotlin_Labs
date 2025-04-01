package com.example.a10lab.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.a10lab.model.WeatherResponse
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.a10lab.ui.theme.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Brush
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud

@Composable
fun WeatherCard(weather: WeatherResponse) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            // Background image based on temperature
            AsyncImage(
                model = when {
                    weather.main.temp >= 27 ->"https://unsplash.com/photos/white-clouds-and-blue-sky-during-daytime-ROVBDer29PQ"// Vibrant sunset
                    weather.main.temp <= 10 -> "https://images.unsplash.com/photo-1483664852095-d6cc6870702d?w=800" // Beautiful snow scene
                    else -> "https://images.unsplash.com/photo-1534088568595-a066f410bcda?w=800" // Pleasant cloudy sky
                },
                contentDescription = "Weather background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            )
            
            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = when {
                                weather.main.temp >= 27 -> listOf(SunnyGradientStart, SunnyGradientEnd)
                                weather.main.temp <= 10 -> listOf(ColdGradientStart, ColdGradientEnd)
                                else -> listOf(CloudyGradientStart, CloudyGradientEnd)
                            }
                        )
                    )
            )

            // Weather information
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextWhite,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when {
                            weather.main.temp >= 27 -> Icons.Default.WbSunny
                            weather.main.temp <= 10 -> Icons.Default.AcUnit
                            else -> Icons.Default.Cloud
                        },
                        contentDescription = null,
                        tint = TextWhite,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${weather.main.temp}°C",
                        style = MaterialTheme.typography.displayLarge,
                        color = TextWhite
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = weather.weather.firstOrNull()?.description?.capitalize() ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    color = VibrantYellow
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherInfoItem(
                        label = "Humidity",
                        value = "${weather.main.humidity}%",
                        icon = Icons.Default.WaterDrop,
                        iconTint = ElectricBlue
                    )
                    WeatherInfoItem(
                        label = "Wind",
                        value = "Moderate",
                        icon = Icons.Default.Air,
                        iconTint = NeonGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherInfoItem(
    label: String,
    value: String,
    icon: ImageVector,
    iconTint: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = label,
                color = TextWhite.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = value,
                color = TextWhite,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}