package com.aa.compose.test

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyButton() {

    val context = LocalContext.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val pressState = interactionSource.collectIsPressedAsState()
    val backgroundColor = if (pressState.value) Color.Green else Color.Red


    Button(
        interactionSource = interactionSource,
        modifier = Modifier.padding(top = 25.dp, start = 25.dp),
        onClick = {
            Toast.makeText(context, "点击了BUTTON", Toast.LENGTH_SHORT).show()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.Blue
        ),
        contentPadding = PaddingValues(25.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "")
        Text(text = "按钮")
    }
}


@Preview
@Composable
fun MyButtonPrew() {
    MyButton()
}