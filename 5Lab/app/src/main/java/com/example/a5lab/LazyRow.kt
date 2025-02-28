package com.example.a5lab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LazyRowExample() {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(20) { index ->
            val image1 = painterResource(id = R.drawable.c1)
            Image(painter = image1, contentDescription = null)

            Text(
                text = "Item $index",
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.LightGray)
                    .padding(16.dp)
            )
        }
    }
}
