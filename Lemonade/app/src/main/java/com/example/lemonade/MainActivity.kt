package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LemonadeApp(){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LemonadeHeader()
        LemonContent()
    }

}

@Composable
fun LemonadeHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFfef08a))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.lemonade),
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

data class ImageData(
    val drawableRes: Int,
    val contentDescriptionRes: Int,
    val descriptionRes: Int
)

val imageList = listOf(
    ImageData(
        drawableRes = R.drawable.lemon_tree,
        contentDescriptionRes = R.string.lemon_tree_content_description,
        descriptionRes = R.string.lemon_tree
    ),
    ImageData(
        drawableRes = R.drawable.lemon_squeeze,
        contentDescriptionRes = R.string.lemon_content_description,
        descriptionRes = R.string.lemon_squeeze
    ),
    ImageData(
        drawableRes = R.drawable.lemon_drink,
        contentDescriptionRes = R.string.glass_of_lemonade_content_description,
        descriptionRes = R.string.lemon_drink
    ),
    ImageData(
        drawableRes = R.drawable.lemon_restart,
        contentDescriptionRes = R.string.empty_glass_content_description,
        descriptionRes = R.string.lemon_restart
    ),
)

fun handleClickButton(step: Int, squeezeTimes: Int): Pair<Int, Int> {
    return when (step) {
        1 -> when {
            squeezeTimes == 0 -> Pair(step, squeezeTimes + 1)
            squeezeTimes == 4 -> Pair(step + 1, squeezeTimes)
            Random.nextBoolean() -> Pair(step + 1, squeezeTimes)
            else -> Pair(step, squeezeTimes + 1)
        }
        3 -> Pair(0, 0)
        else -> Pair(step + 1, 0)
    }
}

@Composable
fun LemonContent() {
    var step by remember { mutableStateOf(0) }
    var squeezeTimes by remember { mutableStateOf(0) }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                val result = handleClickButton(step, squeezeTimes)
                step = result.first
                squeezeTimes = result.second
            },
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFED7AA))

        ) {
            Image(
                painter = painterResource(imageList[step].drawableRes),
                contentDescription = stringResource(imageList[step].contentDescriptionRes)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(imageList[step].descriptionRes),
            fontSize = 18.sp
        )
    }
}

