package com.example.vidoechat.ClassesForRendering

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.vidoechat.R
import com.example.vidoechat.ui.theme.TextBoxColor

class ImageComposableClass {

    var photoUri: Uri? by mutableStateOf(null)

    @Composable
    fun ImageComposable() {
        Image(
            painter = PainterPicker(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(300.dp)
                .border(
                    BorderStroke(5.dp, TextBoxColor),
                    CircleShape
                )
                .padding(top = 5.dp)
                .clip(CircleShape),
        )
    }

    @Composable
    fun PainterPicker(): Painter {
        if (photoUri != null) {
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = photoUri)
                    .build()
            )
            return painter
        }
        val painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(data = R.drawable.plug)
                .build()
        )
        return painter
    }

    fun PhotoURLEdition(uriPhoto: Uri?) {
        photoUri = uriPhoto
    }
}