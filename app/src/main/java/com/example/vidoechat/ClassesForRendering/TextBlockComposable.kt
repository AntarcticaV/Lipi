package com.example.vidoechat.ClassesForRendering

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

class TextBlockComposable {
    private var text = mutableStateOf("")

    @Composable
    fun TextComposable() {
        var textComposable = remember {
            mutableStateOf("")
        }
        textComposable.value = text.value
        Text(
            text = textComposable.value,
            style = TextStyle(color = Color.Black),
            fontFamily = FontFamily.Monospace
        )
    }

    fun TextEdition(textEdition: String) {
        text.value = textEdition
    }
}