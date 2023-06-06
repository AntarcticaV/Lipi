package com.example.vidoechat

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextBlockComposable
import com.example.vidoechat.ClassesForRendering.TextBoxComposable
import com.example.vidoechat.LogicFun.BackActivity
import com.example.vidoechat.LogicFun.OpenActivity
import com.example.vidoechat.LogicFun.userSave
import com.example.vidoechat.ui.theme.BackColor
import com.example.vidoechat.ui.theme.ButtonColor
import com.example.vidoechat.ui.theme.VidoeChatTheme

class SettingAccount : ComponentActivity() {
    val open = mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val emailText = TextBoxComposable()
            emailText.ReadOnlyEdition(true)
            emailText.BeforeTextEdition("Email")
            emailText.PlaceholderEdition(userSave.email)
            val firstnameBox = TextBoxComposable()
            firstnameBox.PlaceholderEdition(userSave.name)
            firstnameBox.BeforeTextEdition("Firstname")
            firstnameBox.ReadOnlyEdition(true)
            val surnameBox = TextBoxComposable()
            surnameBox.ReadOnlyEdition(true)
            surnameBox.BeforeTextEdition("Surname")
            surnameBox.PlaceholderEdition(userSave.surname)
            val nicknameBox = TextBoxComposable()
            nicknameBox.PlaceholderEdition(userSave.nickname)
            nicknameBox.BeforeTextEdition("Nickname")
            nicknameBox.ReadOnlyEdition(true)
            val backButton = ButtonComposable { BackActivity(this) }
            backButton.NameEdition("Back")
            val changeButton = ButtonComposable { OpenChangeDialog() }
            changeButton.NameEdition("Change")
            val buttonChangeNickname =
                ButtonComposable { OpenActivity(this, Intent(this, ChangeNickname::class.java)) }
            buttonChangeNickname.NameEdition("Change Nickname")
            val buttonChangeImage =
                ButtonComposable {  }
            buttonChangeImage.NameEdition("Change Image")
            val buttonChangePassword =
                ButtonComposable { OpenActivity(this, Intent(this, ChangePassword::class.java)) }
            buttonChangePassword.NameEdition("Change Password")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackColor)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column() {
                        Card(
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .height(200.dp)
                                .width(200.dp),
                            shape = RoundedCornerShape(360.dp)
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = painterResource(R.drawable.avatar),
                                contentDescription = ""
                            )
                        }
                    }
                    Column {
                        emailText.TextBoxComposable()
                    }
                    Column {
                        firstnameBox.TextBoxComposable()
                    }
                    Column {
                        surnameBox.TextBoxComposable()
                    }
                    Column {
                        nicknameBox.TextBoxComposable()
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
                            changeButton.MyButtonComposable()
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(60.dp)
                        ) {
                            backButton.MyButtonComposable()
                        }

                        val textTitle = TextBlockComposable()
                        textTitle.TextEdition("Change")
                        if (open.value) {
                            AlertDialog(onDismissRequest = { CloceDialog() }, {
                                Surface() {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Column() {
                                            Text(
                                                text = "What do you want to change?",
                                                style = TextStyle(color = Color.Black),
                                                fontSize = 20.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(top = 10.dp)
                                                .fillMaxWidth()
                                                .height(60.dp)
                                        ) {
                                            buttonChangeNickname.MyButtonComposable()
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(top = 10.dp)
                                                .fillMaxWidth()
                                                .height(60.dp)
                                        ) {
                                            buttonChangeImage.MyButtonComposable()
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(top = 10.dp)
                                                .fillMaxWidth()
                                                .height(60.dp)
                                        ) {
                                            buttonChangePassword.MyButtonComposable()
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    fun OpenChangeDialog() {
        open.value = true
    }

    fun CloceDialog() {
        open.value = false
    }
}

