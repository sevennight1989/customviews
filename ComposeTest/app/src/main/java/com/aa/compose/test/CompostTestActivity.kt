package com.aa.compose.test

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.aa.compose.test.ui.theme.ComposeTestTheme

class CompostTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            TextDemo5(context = this)
//            MyTextFiled()
//            MyButton()
//            MySlider()
            ComposeTestTheme {
//                MyApp(modifier = Modifier.fillMaxSize())
                MyAppNext(modifier = Modifier.fillMaxSize())
            }
//            OnboardingScreen()
        }
    }

    @Composable
    fun TextDemo5(context: Context) {
        ClickableText(text = AnnotatedString("请点击我"), onClick = { index ->
            Toast.makeText(context, "点击位置:$index", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.padding(top = 15.dp))
        Image(
            painter = painterResource(id = R.drawable.avatar), contentDescription = "avatar",
            modifier = Modifier
                .padding(top = 35.dp)
                .size(60.dp)
        )
        val bitmap1 = ImageBitmap.imageResource(id = R.drawable.avatar)
        Image(
            bitmap = bitmap1, contentDescription = "avatar",
            modifier = Modifier
                .padding(top = 100.dp)
                .size(65.dp)
        )
        Icon(
            imageVector = Icons.Default.Add, contentDescription = "add", modifier = Modifier
                .padding(top = 165.dp)
                .size(40.dp),
            tint = Color.Red
        )
        Image(
            painter = rememberImagePainter(data = "https://img0.baidu.com/it/u=2702865616,2265069128&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800", builder = {
                placeholder(R.mipmap.ic_launcher)
            }),
            modifier = Modifier
                .padding(top = 205.dp)
                .size(100.dp)
            , contentDescription = "net"
        )
    }

    @Preview(name = "Light Mode")
    @Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
    @Composable
    fun GreetingPreview() {
        ComposeTestTheme(darkTheme = true) {
            TextDemo5(this)
        }
    }
}