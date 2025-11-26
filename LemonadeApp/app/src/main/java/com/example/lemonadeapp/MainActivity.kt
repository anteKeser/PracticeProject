package com.example.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonadeapp.ui.theme.LemonadeAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeAppTheme {
                LemonadeApp()
            }
        }
    }
}

@Preview
@Composable
fun LemonadeApp(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBar()
        Spacer(modifier = modifier.height(250.dp))
        LemonadeTextAndImage(modifier = modifier)
    }

}


@Composable
fun LemonadeTextAndImage(modifier: Modifier) {
    var index by remember { mutableStateOf(1) }
    var clicks = 0
    Button(
        onClick = {
            when (index) {
                1 -> {
                    if (clicks == 0) clicks = (1..3).random()
                    else clicks--
                    if (clicks == 0) index++
                }

                3 -> {
                    index = 0
                }

                else -> {
                    index++
                }
            }

        },
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
    ) {
        Image(
            painterResource(
                when (index) {
                    0 -> R.drawable.lemon_tree
                    1 -> R.drawable.lemon_squeeze
                    2 -> R.drawable.lemon_drink
                    else -> R.drawable.lemon_restart
                }
            ),
            contentDescription = null
        )
    }
    Text(
        text = when (index) {
            0 -> stringResource(R.string.lemon_tree)
            1 -> stringResource(R.string.lemon_squeeze)
            2 -> stringResource(R.string.lemonade_drink)
            else -> stringResource(R.string.restart)
        },
        modifier = modifier.padding(20.dp),
        fontSize = 15.sp
    )
}


@Composable
fun AppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Yellow)
            .height(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.title), fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}