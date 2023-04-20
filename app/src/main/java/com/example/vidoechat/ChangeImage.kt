package com.example.vidoechat

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.ImageComposableClass
import com.example.vidoechat.LogicFun.BackActivity
import com.example.vidoechat.ui.theme.BackColor
import com.example.vidoechat.ui.theme.VidoeChatTheme

class ChangeImage : ComponentActivity() {
    var photoUri: Uri? by mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                    photoUri = uri
                }
            val buttonSave = ButtonComposable { BackActivity(this) }
            buttonSave.NameEdition("Save")
            val buttonBack = ButtonComposable { BackActivity(this) }
            buttonBack.NameEdition("Back")
            val changeImage = ButtonComposable {
                launcher.launch(
                    PickVisualMediaRequest(

                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
            val imagePaint = ImageComposableClass()
            imagePaint.PhotoURLEdition(photoUri)
            changeImage.NameEdition("Change Image")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        imagePaint.ImageComposable()
                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .fillMaxWidth(0.9f)
                            .height(60.dp)
                    ) {
                        changeImage.MyButtonComposable()
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.48f)
                                .height(60.dp)
                        ) {
                            buttonSave.MyButtonComposable()
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(60.dp)
                        ) {
                            buttonBack.MyButtonComposable()
                        }
                    }
                }
            }
        }
    }


    fun PickImage() {

    }
}
