package com.example.vidoechat.ClassesForRendering

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidoechat.ui.theme.MyLightGray
import com.example.vidoechat.ui.theme.TextBoxColor
import kotlin.properties.ReadOnlyProperty

class TextBoxComposable {
    private val textGlo = mutableStateOf("")
    private val textPlaceholder = mutableStateOf("")
    private val textBefore = mutableStateOf("")
    private val readOnlyBoll = mutableStateOf(false)

    @Composable
    fun TextBoxComposable() {
        var text = remember {
            mutableStateOf("")
        }
        val textPla = remember {
            mutableStateOf("")
        }
        val textBef = remember {
            mutableStateOf("")
        }
        val readOnly = remember {
            mutableStateOf(false)
        }
        textGlo.value = text.value
        textPla.value = textPlaceholder.value
        textBef.value = textBefore.value
        readOnly.value = readOnlyBoll.value
        Column (modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = textBef.value,
                style = TextStyle(color = Color.Black),
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace)
            Card(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp
            ) {
                TextField(
                    value = text.value,
                    onValueChange = { newText -> text.value = newText },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text(textPla.value, fontFamily = FontFamily.Monospace) },
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = TextBoxColor),
                    readOnly = readOnly.value
                )
            }
        }
    }

    fun BeforeTextEdition(text : String){
        textBefore.value = text
    }

    fun PlaceholderEdition(textEdition: String) {
        textPlaceholder.value = textEdition
    }

    fun ReturnPassword(): String {
        return textGlo.value
    }

    fun ReadOnlyEdition(readOnly:Boolean){
        readOnlyBoll.value = readOnly
    }
}