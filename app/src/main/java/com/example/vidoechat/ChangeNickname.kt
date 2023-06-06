package com.example.vidoechat

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextBoxComposable
import com.example.vidoechat.LogicFun.BackActivity
import com.example.vidoechat.LogicFun.userSave
import com.example.vidoechat.Models.ChechNick
import com.example.vidoechat.NewWork.APIService
import com.example.vidoechat.ui.theme.BackColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeNickname : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val oldNickname = TextBoxComposable()
            oldNickname.ReadOnlyEdition(true)
            oldNickname.PlaceholderEdition(userSave.nickname)
            oldNickname.BeforeTextEdition("Old Nickname")
            val changeNickname = TextBoxComposable()
            changeNickname.PlaceholderEdition("give me you're new Nickname")
            changeNickname.BeforeTextEdition("New Nickname")
            val buttonSave = ButtonComposable {
                ChangeNicknameUser(
                    changeNickname.ReturnPassword()
                )
            }
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
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f)
                            .size(300.dp),
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = ""
                    )

                    Column {
                        oldNickname.TextBoxComposable()
                    }
                    Column {
                        changeNickname.TextBoxComposable()
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

    fun ChangeNicknameUser(newNickname: String){
        lifecycleScope.launch(Dispatchers.IO) {
            val api = APIService.api.ChenchNickname(ChechNick( userSave.nickname, newNickname))
            if (api.status){
                this@ChangeNickname.finish()
            }
        }
    }
}

