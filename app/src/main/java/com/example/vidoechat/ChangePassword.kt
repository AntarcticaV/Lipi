package com.example.vidoechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextFieldPasswordComposable
import com.example.vidoechat.LogicFun.BackActivity
import com.example.vidoechat.LogicFun.ChackPassword
import com.example.vidoechat.LogicFun.userSave
import com.example.vidoechat.Models.ChechPas
import com.example.vidoechat.NewWork.APIService
import com.example.vidoechat.ui.theme.BackColor
import com.example.vidoechat.ui.theme.VidoeChatTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChangePassword : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val oldPassword = TextFieldPasswordComposable()
            oldPassword.BeforeTextEdition("Password")
            oldPassword.PlaceholderEdition("Need you're old password")
            val newPassword = TextFieldPasswordComposable()
            newPassword.PlaceholderEdition("New Password")
            newPassword.BeforeTextEdition("New Password")
            val newPasswordConfirm = TextFieldPasswordComposable()
            newPasswordConfirm.BeforeTextEdition("Confirm new password")
            newPasswordConfirm.PlaceholderEdition("Confirm new password")
            val buttonSave = ButtonComposable {
                ChangePasswordUser(
                    oldPassword.ReturnPassword(),
                    newPassword.ReturnPassword(),
                    newPasswordConfirm.ReturnPassword()
                ) }
            buttonSave.NameEdition("Save")
            val buttonBack = ButtonComposable { BackActivity(this) }
            buttonBack.NameEdition("Back")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackColor)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Column() {
                        oldPassword.TextFieldPasswordComposable()
                        newPassword.TextFieldPasswordComposable()
                        newPasswordConfirm.TextFieldPasswordComposable()
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

    fun ChangePasswordUser(password: String, newPassword:String, newPasswordConfirm: String){
        if (ChackPassword(password, userSave.password) && ChackPassword(newPassword, newPasswordConfirm)){
            lifecycleScope.launch(Dispatchers.IO) {
                val api = APIService.api.ChanchPassword(ChechPas(userSave.nickname, password, newPassword ))
                withContext(Dispatchers.Main){
                    if (api.status){
                        this@ChangePassword.finish()
                    }
                }
            }
        }

    }
}
