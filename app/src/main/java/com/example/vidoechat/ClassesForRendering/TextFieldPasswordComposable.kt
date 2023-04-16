package com.example.vidoechat.ClassesForRendering

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidoechat.ui.theme.MyLightGray

class TextFieldPasswordComposable {
    private val passwordGlo = mutableStateOf("")
    private val passwordVisibleGlo = mutableStateOf(false)
    private val textPlaceholder = mutableStateOf("")
    private val textBefore = mutableStateOf("")

    @Composable
    fun TextFieldPasswordComposable() {
        val password = remember {
            mutableStateOf("")
        }
        val passwordVisible = remember {
            mutableStateOf(false)
        }
        val textPla = remember {
            mutableStateOf("")
        }
        val textBef = remember {
            mutableStateOf("")
        }
        passwordGlo.value = password.value
        passwordVisibleGlo.value = passwordVisible.value
        textPla.value = textPlaceholder.value
        textBef.value = textBefore.value
        Column (modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = textBef.value,
                style = TextStyle(color = Color.Black),
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace
            )
            Card(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxWidth(0.99f),
                shape = RoundedCornerShape(15.dp),
                elevation = 5.dp
            ) {
                TextField(
                    value = password.value,
                    onValueChange = { newText -> password.value = newText },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                    placeholder = { Text(textPla.value, fontFamily = FontFamily.Monospace) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MyLightGray),
                    visualTransformation = if (passwordVisible.value)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible.value)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description =
                            if (passwordVisible.value) "Hide password" else "Show password"
                        IconButton(onClick = {
                            passwordVisible.value = !passwordVisible.value
                        }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
            }
        }
    }

    fun PlaceholderEdition(textEdition: String) {
        textPlaceholder.value = textEdition
    }

    fun BeforeTextEdition(text : String){
        textBefore.value = text
    }

    fun ReturnPassword(): String {
        return passwordGlo.value
    }
}