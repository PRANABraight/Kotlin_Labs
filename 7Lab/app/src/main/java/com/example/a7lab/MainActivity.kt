package com.example.a7lab

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.a7lab.ui.theme._7LabTheme

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"
    private val lifecycleEvents = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        lifecycleEvents.add("onCreate called")
        enableEdgeToEdge()
        setContent {
            _7LabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        lifecycleEvents = lifecycleEvents,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
        lifecycleEvents.add("onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
        lifecycleEvents.add("onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
        lifecycleEvents.add("onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
        lifecycleEvents.add("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
        lifecycleEvents.add("onDestroy called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart called")
        lifecycleEvents.add("onRestart called")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Greeting(lifecycleEvents: List<String>, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }

    val eventColors = mapOf(
        "onCreate called" to Color(0xFFBBDEFB),
        "onStart called" to Color(0xFFC8E6C9),
        "onResume called" to Color(0xFFFFF9C4),
        "onPause called" to Color(0xFFFFCCBC),
        "onStop called" to Color(0xFFD1C4E9),
        "onDestroy called" to Color(0xFFFFCDD2),
        "onRestart called" to Color(0xFFFFF176)
    )

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Activity Lifecycle Demo",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(lifecycleEvents) { event ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = eventColors[event] ?: MaterialTheme.colorScheme.surfaceVariant
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                        Text(
                            text = event,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        ElevatedButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                "Lifecycle Information",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "Activity Lifecycle States",
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LifecycleState("onCreate", "Activity is being created")
                    LifecycleState("onStart", "Activity is becoming visible")
                    LifecycleState("onResume", "Activity is ready to interact")
                    LifecycleState("onPause", "Activity is partially hidden")
                    LifecycleState("onStop", "Activity is no longer visible")
                    LifecycleState("onDestroy", "Activity is being destroyed")
                    LifecycleState("onRestart", "Activity is restarting")
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun LifecycleState(state: String, description: String) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = state,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}