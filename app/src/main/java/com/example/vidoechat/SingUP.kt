package com.example.vidoechat

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextBlockComposable
import com.example.vidoechat.ClassesForRendering.TextBoxComposable
import com.example.vidoechat.ClassesForRendering.TextFieldPasswordComposable
import com.example.vidoechat.LogicFun.BackActivity
import com.example.vidoechat.LogicFun.ChackPassword
import com.example.vidoechat.ui.theme.BackColor

class SingUP : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val emailBox = TextBoxComposable()
            emailBox.PlaceholderEdition("Email")
            emailBox.BeforeTextEdition("Email:")
            val firstname = TextBoxComposable()
            firstname.PlaceholderEdition("Firstname")
            firstname.BeforeTextEdition("Firstname:")
            val surname = TextBoxComposable()
            surname.PlaceholderEdition("Surname")
            surname.BeforeTextEdition("Surname:")
            val nickname = TextBoxComposable()
            nickname.PlaceholderEdition("Nickname")
            nickname.BeforeTextEdition("Nickname:")
            val password = TextFieldPasswordComposable()
            password.PlaceholderEdition("Password")
            password.BeforeTextEdition("Password:")
            val passwordConfirm = TextFieldPasswordComposable()
            passwordConfirm.PlaceholderEdition("Password")
            passwordConfirm.BeforeTextEdition("Password confirm:")
            val backButton = ButtonComposable { BackActivity(this) }
            backButton.NameEdition("Back")
            val singUpButton = ButtonComposable {
                OpenMainActivity(
                    this,
                    password.ReturnPassword(),
                    passwordConfirm.ReturnPassword()
                )
            }
            singUpButton.NameEdition("Sing up")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackColor)
            ) {
                emailBox.TextBoxComposable()
                firstname.TextBoxComposable()
                surname.TextBoxComposable()
                nickname.TextBoxComposable()
                password.TextFieldPasswordComposable()
                passwordConfirm.TextFieldPasswordComposable()

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.35f)
                            .height(60.dp)
                    ) {
                        singUpButton.MyButtonComposable()
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(60.dp)
                    ) {
                        backButton.MyButtonComposable()
                    }
                }
            }
        }
    }

    fun OpenMainActivity(context: Context, password: String, passwordConfirm: String) {
        if (ChackPassword(password, passwordConfirm))
        //context.startActivity(Intent(context, MainActivity::class.java))
            this.finish()
    }
}

