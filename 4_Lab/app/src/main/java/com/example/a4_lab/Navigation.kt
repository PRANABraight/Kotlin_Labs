package com.example.a4_lab

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("details/{name}/{age}/{course}") { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
                val age = backStackEntry.arguments?.getString("age") ?: "Unknown"
                val course = backStackEntry.arguments?.getString("course") ?: "Unknown"

                DetailsScreen(name, age, course, navController)
            }
        }
    }
}