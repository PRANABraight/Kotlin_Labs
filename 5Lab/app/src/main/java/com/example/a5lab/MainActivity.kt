package com.example.a5lab

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a5lab.ui.theme._5LabTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _5LabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray),
                            title = { Text(text = "Blog")
                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray),
                        ) { Text(text = "Bottom App Bar") }
                    }
                ) {
                    var viewType by remember { mutableStateOf(ViewType.COLUMN) }

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.padding(it)) {
                            Box(modifier = Modifier.padding(8.dp)) {
                                LazyRowCarousel()
                            }

//                            ToggleViewButton(viewType) { viewType = it }

                            Box(modifier = Modifier.padding(8.dp)) {
                                when (viewType) {
                                    ViewType.COLUMN -> LazyColumnExample()
                                    ViewType.GRID -> LazyVerticalGridExample()
                                }
                            }
                        }



                        FloatingActionButton(
                            onClick = {
                                val newViewType = if (viewType == ViewType.COLUMN) ViewType.GRID else ViewType.COLUMN
                                viewType = newViewType
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LazyRowCarousel() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(4) { index ->
            val image1 = painterResource(id = R.drawable.c1)
            val image2 = painterResource(id = R.drawable.c2)
            val image3 = painterResource(id = R.drawable.c3)

            Image(painter = image1, contentDescription = null,
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.padding(16.dp))

            Image(painter = image2, contentDescription = null,
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.padding(16.dp))

            Image(painter = image3, contentDescription = null,
                modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}

enum class ViewType {
    COLUMN, GRID
}

@Composable
fun LazyColumnExample() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(20) { index ->
            Row {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).align(Alignment.CenterVertically)



                    )
                Text(

                    text = "$index lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
                            "eiusmod tempor incididunt ut labore et dolore magna aliqua.",                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.hsl(1f, 0.8f, 0.9f))
                        .padding(16.dp)
                        .fillMaxWidth()


                )

            }
        }
    }
}

