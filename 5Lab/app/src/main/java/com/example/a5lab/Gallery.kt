package com.example.a5lab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LazyVerticalGridExample() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(12) { index ->
            val imageRes = when (index % 3) {
                0 -> R.drawable.c1
                1 -> R.drawable.c2
                else -> R.drawable.c3
            }
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.LightGray)
            )
        }
    }
}


@Composable
fun LazyRowCarousel() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(1) { index ->

            Box {
                Image(
                    painter = painterResource(id = R.drawable.c3),
                    contentDescription = null,
                    modifier = Modifier
                )
                Text(
                    text = "Great Hike at Tiger Hill",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
            }
                Spacer(modifier = Modifier.width(16.dp))
            Box {
                Image(
                    painter = painterResource(id = R.drawable.c2),

                    contentDescription = null,
                    modifier = Modifier
                )
                Text(
                    text = "My Sweet Story",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
            }
                Spacer(modifier = Modifier.width(16.dp))
            Box {
                Image(
                    painter = painterResource(id = R.drawable.c1),

                    contentDescription = null,
                    modifier = Modifier
                )
                Text(
                    text = "My Family, My support",
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )

            }



            Spacer(modifier = Modifier.width(16.dp))


        }
    }
}

@Composable
fun LazyColumnExample() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {

        items(3) { index ->
            Image(painter = painterResource(id = R.drawable.c1), contentDescription = null, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Image(painter = painterResource(id = R.drawable.c2), contentDescription = null, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            Image(painter = painterResource(id = R.drawable.c3), contentDescription = null, modifier = Modifier.fillMaxWidth())

        }
    }
}



enum class ViewType {
    COLUMN, GRID;}
