package com.aa.compose.test

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTextFiled() {
    val name = remember {
        mutableStateOf("")
    }
//    TextField(value = name.value, onValueChange = {
//    OutlinedTextField(value = name.value, onValueChange = {
//        name.value = it
//        Log.i(TAG, "textcontent:${name.value}")
//
//    }, modifier = Modifier.padding(top = 15.dp),
//        textStyle = TextStyle(fontSize = 25.sp, color = Color.Red),
//        label = {
//            Text(text = "Name")
//        },
//        placeholder = {
//            Text(text = "Please input name:")
//        },
//        visualTransformation = PasswordVisualTransformation()
//    )

    BasicTextField(

        value = name.value,
        onValueChange = {
            name.value = it
            Log.i(TAG, "textcontent:${name.value}")
        },
        modifier = Modifier
            .padding(top = 50.dp)
            .border(2.dp, color = Color.Green)
            .padding(top = 50.dp)
            .background(Color.Yellow),

        )

}


@Preview
@Composable
fun MyTextFiledPrew() {
    MyTextFiled()
}