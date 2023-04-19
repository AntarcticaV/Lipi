package com.example.vidoechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextBlockComposable
import com.example.vidoechat.ClassesForRendering.TextBoxComposable
import com.example.vidoechat.LogicFun.BackActivity
import com.example.vidoechat.LogicFun.OpenActivity
import com.example.vidoechat.ui.theme.BackColor
import com.example.vidoechat.ui.theme.VidoeChatTheme

class FunctionalMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tokenEnter = TextBoxComposable()
            tokenEnter.BeforeTextEdition("Enter the token to connect")
            tokenEnter.PlaceholderEdition("Token")
            val buttonConnect = ButtonComposable { BackActivity(this) }
            buttonConnect.NameEdition("Connect")
            val buttonSettingAccount =
                ButtonComposable { OpenActivity(this, Intent(this, SettingAccount::class.java)) }
            buttonSettingAccount.NameEdition("Setting account")
            val buttonLogout = ButtonComposable { BackActivity(this) }
            buttonLogout.NameEdition("Logout")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackColor)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Column() {
                        tokenEnter.TextBoxComposable()
                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(0.9f)
                            .height(60.dp)
                    ) {
                        buttonConnect.MyButtonComposable()
                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(0.9f)
                            .height(60.dp)
                    ) {
                        buttonSettingAccount.MyButtonComposable()
                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(0.9f)
                            .height(60.dp)
                    ) {
                        buttonLogout.MyButtonComposable()
                    }
                }
            }
        }
    }

    fun OpenSetting(context: Context) {
        context.startActivity(Intent(context, SettingAccount::class.java))
    }
}