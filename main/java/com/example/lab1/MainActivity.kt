
package com.example.lab1


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab1.ui.theme.Lab1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Wish(name = "Mom", from = "Son, Pranab") {
                        Toast.makeText(this, "Share Happiest Birthday", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


@Composable
fun Wish(name: String, from: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bday_card),
            contentDescription = "Image background",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Happy Birthday, \n $name!",
                    fontSize = 30.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                    lineHeight = 30.sp,
//                    contentDescription = "title",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Wishing you a day filled with happiness and a year filled with joy. \n Happy Birthday!",
                    fontSize = 16.sp,
                    color = Color.DarkGray,
//                    contentDescription= "body",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(30.dp)
                )
                Text(
                    text = "From Your Loving \n $from",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End).padding(20.dp)
                )
            }
            Button(
                onClick = onClick,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Share")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab1Theme {
        Wish("Mom", "Son, Pranab") {}
    }
}


