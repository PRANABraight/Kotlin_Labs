package com.example.a4_lab;

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DetailsScreen(name: String, age: String, course: String, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Details Screen", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Name: $name", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Age: $age", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Course: $course", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigateUp() }) {
            Text("Go Back")
        }
    }
}
