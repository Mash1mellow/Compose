package com.alexander.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.alexander.compose.ui.theme.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("多指觸控Compose實例")
                }
            }
        }
    }
}

data class Points(
    val x: Float,
    val y: Float
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Greeting(name: String) {
    var PaintColor:Color
    var colors = arrayListOf(
        ColorRed, ColorOrange, ColorYellow, ColorGreen,
        ColorBlue, ColorIndigo, ColorPurple, ColorCyan
    )
    var Touches = remember { mutableStateListOf<Points>() }
    var Fingers = remember { mutableStateOf (0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInteropFilter { event ->
                Fingers.value = event.getPointerCount()
                Touches.clear()
                for (i in 0..Fingers.value - 1) {
                    Touches += Points(event.getX(i), event.getY(i))
                }
                true
            }
    ){
        Canvas(modifier = Modifier){
//drawCircle(Color.Yellow, 100f, Offset(200f, 300f))
            var i = 0
            for (p in Touches) {
                PaintColor = colors[i % 7]
                drawCircle(PaintColor, 25f, Offset(p.x, p.y))
                i++
            }
        }
    }
    Column {
        Row {
            Text(
                text = "$name",
                fontFamily = FontFamily(Font(R.font.kai)),
                fontSize = 25.sp,
                color = Color.Cyan
            )

            Image(
                painter = painterResource(id = R.drawable.hand),
                contentDescription = "手掌圖片",
                alpha = 0.7f,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Red)
            )
        }
        Text(text = "作著 : 黃植達")
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var count = remember { mutableStateOf(0) }
            Text(
                text = count.value.toString(),
                fontSize = 50.sp,
                modifier = Modifier.clickable { count.value += 1 })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Greeting("多指觸控Compose實例")
    }
}