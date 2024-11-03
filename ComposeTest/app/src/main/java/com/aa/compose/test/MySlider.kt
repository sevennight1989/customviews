package com.aa.compose.test

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MySlider() {
    val TAG = "MySlider"
    Surface(
        color = Color.Cyan,
        shadowElevation = 5.dp,
        modifier = Modifier.padding(top = 25.dp, start = 10.dp, end = 10.dp)
    ) {
        var sliderPosition by remember {
            mutableStateOf(0.6f)
        }
        Column {
            Slider(
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Green,
                    inactiveTrackColor = Color.Blue
                ),
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                },
                onValueChangeFinished= {
                    Log.i(TAG,"onValueChangeFinished")
                }
            )
            Text(text = (100 * sliderPosition).toInt().toString())
        }
    }
}

@Preview
@Composable
fun MySliderPrev() {
    MySlider()
}
