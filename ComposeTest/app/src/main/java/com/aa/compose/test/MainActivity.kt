package com.aa.compose.test

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aa.compose.test.ui.theme.ComposeTestTheme

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTestTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentColor = colorResource(id = R.color.teal_700)
                ) { innerPadding ->

                    val messages: MutableList<Message> = mutableListOf(
                        Message("Lexi", "Test...Test,,,Test,,,"),
                        Message("Lexi", "只包含一条消息的聊天略显孤单。"),
                        Message(
                            "Lexi",
                            "因此我们将更改对话，使其包含多条消息。您需要创建一个可显示多条消息的 Conversation 函数"
                        ),
                        Message(
                            "Lexi",
                            "对于此用例，请使用 Compose 的 LazyColumn 和 LazyRow。这些可组合项只会呈现屏幕上显示的元素，因此，对于较长的列表，使用它们会非常高效"
                        ),
                        Message("Lexi", "在此代码段中"),
                        Message("Lexi", "您可以看到 LazyColumn 包含一个 items 子项"),
                        Message(
                            "Lexi",
                            "它接受 List 作为参数，并且其 lambda 会收到我们命名为 message 的参数（可以随意为其命名），该参数是 Message 的实例"
                        ),
                        Message("Lexi", "简而言之，系统会针对提供的 List 的每个项调用此 lambda"),
                        Message("Lexi", "将示例数据集复制到您的项目中，以便快速引导对话"),
                    )
                    messages.add(Message("Peter", "I don't like blue!"))
                    messages.add(
                        Message(
                            "Peter",
                            "I don't like b®lue! Yes! 123456789999I also lick apple."
                        )
                    )

                    LazyColumn {
                        items(messages) { message ->
                            MessageCard(msg = message, Modifier.padding(innerPadding))
                        }
                    }

                    val textString = buildAnnotatedString {
                        append("Hello")
                        pushStringAnnotation(
                            tag = "tag0",
                            annotation = "Compose111"
                        )
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("Compose")
                        }
                        pop()
                        append("1111")
                    }

                    ClickableText(text = AnnotatedString("请点击我"), onClick = { index ->
                       Log.i(TAG,"点击位置:$index")
                    }, modifier = Modifier.padding(top = 25.dp))

                    ClickableText(text = textString,
                        onClick = { it ->
                            Log.i(TAG, "Click:$it")
                            textString.getStringAnnotations("tag0", it, it).firstOrNull().let {
                                Log.i(TAG, "Click" + it?.item)
                            }
                        },modifier = Modifier.padding(top = 55.dp))



//                    MessageCard(
//                        msg = Message("Peter", "I don't like blue!"), Modifier.padding(innerPadding)
//                    )
//                    ShowText(
//                        name = "AndroidNext",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
            }
        }
    }
}

@Composable
fun ShowText(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
//        stringResource(id = R.string.app_name),
        modifier = modifier
            .width(350.dp)
            .height(100.dp),
        textAlign = TextAlign.Center,
        color = Color.Red,
        fontSize = 30.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
    )
}

data class Message(val author: String, val body: String)



@Composable
fun MessageCard(msg: Message, modifier: Modifier = Modifier) {
    Row(modifier = Modifier.padding(top = 80.dp)) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "launcher",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)

        )
        Spacer(modifier = Modifier.width(10.dp))
        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            label = "",
        )
        Column(modifier = Modifier.clickable {
            Log.i(TAG, "Before isExpanded:$isExpanded")
            isExpanded = !isExpanded
            Log.i(TAG, "After isExpanded:$isExpanded")
        }
        ) {

            Text(
                text = msg.author,
//                modifier = modifier,
//            .width(350.dp),
//            .height(100.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
//                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 2.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(all = 4.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                    //                fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }


}

@Preview(name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark mode")
@Composable
fun GreetingPreview() {
    ComposeTestTheme(darkTheme = true) {
//        ShowText("AndroidNext")
        MessageCard(msg = Message("Tom", "I like red!"))
    }
}