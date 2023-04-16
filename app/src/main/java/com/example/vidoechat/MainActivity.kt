package com.example.vidoechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextBoxComposable
import com.example.vidoechat.ClassesForRendering.TextFieldPasswordComposable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val password = TextFieldPasswordComposable()
            password.PlaceholderEdition("Password")
            password.BeforeTextEdition("Password:")
            val loginBox = TextBoxComposable()
            loginBox.PlaceholderEdition("Login")
            loginBox.BeforeTextEdition("Login:")
            val buttonSingup = ButtonComposable { OpenSingUp(this) }
            buttonSingup.NameEdition("Sing up")
            val buttonSingin = ButtonComposable { OpenSingIn(this) }
            buttonSingin.NameEdition("Sing in")
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.lipi_logo2),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .padding(top = 100.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                )
                {
                    loginBox.TextBoxComposable()
                    password.TextFieldPasswordComposable()
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(0.7f)
                            .height(60.dp)
                    ) {
                        buttonSingin.MyButtonComposable()

                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(0.7f)
                            .height(60.dp)
                    ) {
                        buttonSingup.MyButtonComposable()
                    }

                }
            }
        }
    }

    fun OpenSingUp(content: Context) {
        content.startActivity(Intent(content, SingUP::class.java))
    }

    fun OpenSingIn(content: Context) {
        content.startActivity(Intent(content, SingIN::class.java))
    }
}

