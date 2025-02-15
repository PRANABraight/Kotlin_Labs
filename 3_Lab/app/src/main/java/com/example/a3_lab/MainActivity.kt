package com.example.a3_lab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = { AppBottomBar() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        CurrencyConverter(
            modifier = Modifier.padding(innerPadding),
            onConversionSuccess = {
                scope.launch {
                    snackbarHostState.showSnackbar("Conversion Successful!")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    TopAppBar(
        title = { Text("Convertio - Currency Converter", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun AppBottomBar() {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = "© 2025 Convertio",
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CurrencyConverter(modifier: Modifier = Modifier, onConversionSuccess: () -> Unit) {
    var inputAmount by remember { mutableStateOf("") }
    var outputAmount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("EUR") }
    val conversionRate = mapOf("USD" to 1.0, "EUR" to 0.85, "INR" to 75.0, "JPY" to 110.0, "GBP" to 0.75, "AUD" to 1.4)

    val currencyOptions = listOf("USD", "EUR", "INR", "JPY", "GBP", "AUD")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.currency),
            contentDescription = "Currency Converter Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Currency Converter",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputAmount,
            onValueChange = { inputAmount = it },
            label = { Text("Enter Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            CurrencyDropdown(
                selectedCurrency = fromCurrency,
                onCurrencySelected = { fromCurrency = it },
                currencyOptions = currencyOptions
            )

            Icon(imageVector = Icons.Default.SwapHoriz, contentDescription = "Swap", tint = Color.Gray)

            CurrencyDropdown(
                selectedCurrency = toCurrency,
                onCurrencySelected = { toCurrency = it },
                currencyOptions = currencyOptions
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        FilledTonalButton(
            onClick = {
                val inputValue = inputAmount.toDoubleOrNull() ?: 0.0
                val fromRate = conversionRate[fromCurrency] ?: 1.0
                val toRate = conversionRate[toCurrency] ?: 1.0
                outputAmount = String.format("%.2f", (inputValue / fromRate) * toRate)
                onConversionSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Converted Amount: $outputAmount $toCurrency",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CurrencyDropdown(selectedCurrency: String, onCurrencySelected: (String) -> Unit, currencyOptions: List<String>) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = selectedCurrency)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencyOptions.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterPreview() {
    CurrencyConverterApp()
}