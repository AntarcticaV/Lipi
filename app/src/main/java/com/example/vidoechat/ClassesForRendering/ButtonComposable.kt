package com.example.vidoechat.ClassesForRendering

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidoechat.ui.theme.MyDarkGray

class ButtonComposable(funkOnClick:()->Unit) {
    private val nameButtonGlo = mutableStateOf("")
    private val funClick = funkOnClick

    @Composable
    fun MyButtonComposable() {
        val nameButton = remember {
            mutableStateOf("")
        }

        nameButton.value = nameButtonGlo.value
        Button(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(15.dp),
            onClick = { OnClickMyButton(funClick) },
            colors = ButtonDefaults.buttonColors(backgroundColor = MyDarkGray)
        ) {
            Text(
                text = nameButton.value,
                fontSize = 20.sp,
                style = TextStyle(color = Color.Black),
                fontFamily = FontFamily.Monospace
            )
        }
    }

    private fun OnClickMyButton(funkOnClick: () -> Unit) {
        funkOnClick()
    }

    fun NameEdition(name: String) {
        nameButtonGlo.value = name
    }
}