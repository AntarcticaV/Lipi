package com.example.vidoechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.internal.liveLiteral
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextBoxComposable
import com.example.vidoechat.ClassesForRendering.TextFieldPasswordComposable
import com.example.vidoechat.LogicFun.AddUser
import com.example.vidoechat.LogicFun.OpenActivity
import com.example.vidoechat.Models.Auth
import com.example.vidoechat.NewWork.APIService
import com.example.vidoechat.ui.theme.BackColor
import com.example.vidoechat.ui.theme.TextBoxColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = BackColor, modifier = Modifier.fillMaxSize()) {
                Navigation(this)
            }
        }
    }

    @Composable
    fun Navigation(context: Context) {
        val navController = rememberNavController()
        NavHost( // навигация в верстке
            navController = navController,
            startDestination = "splash_screen"
        ) {
            composable("splash_screen") { // запуск экрана загрузки
                SplashScreen(navController = navController)
            }
            // Main Screen
            composable("main_screen") { // запуск экрана авторизации

                val password = TextFieldPasswordComposable() // создание экземпляра класса для поля ввода пароля
                password.PlaceholderEdition("Password")
                password.BeforeTextEdition("Password:")
                val loginBox = TextBoxComposable() // создание создание экземпляра класса для поля ввода логина
                loginBox.PlaceholderEdition("Login")
                loginBox.BeforeTextEdition("Login:")
                val buttonSingup =
                    ButtonComposable {
                        OpenActivity(context, Intent(context, SingUP::class.java)) // передача функции для открытия нового экрана
                    } // создание экземпляра класса для кнопки регистрации
                buttonSingup.NameEdition("Sing up")
                val buttonSingin =
                    ButtonComposable {
                        AuthenticateUser(loginBox.ReturnPassword(),password.ReturnPassword(), this@MainActivity) // передача функции для отправки данных пользователя для проверки существования его
                    } // создание экземпляра класса для кнопки входа
                buttonSingin.NameEdition("Sing in")
                Column( // контейнер который располагает элементы внутри себя по горизонтале
                    modifier = Modifier
                        .fillMaxSize() // указание размера контейнера по высоте и ширине
                        .background(BackColor) // указание цведа контейнера
                        .verticalScroll(rememberScrollState()), // задача возможности пролистыва контейнер
                    verticalArrangement = Arrangement.Center, // указание расположения элементов по вертикале
                    horizontalAlignment = Alignment.CenterHorizontally // указание расположения элементов по горизонтали
                )
                {
                    Image( // объект картинки
                        painter = painterResource(id = R.drawable.lipi_logo2),
                        contentDescription = "",
                        modifier = Modifier
                            .size(300.dp)
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
                        loginBox.TextBoxComposable() // отрисовка поля для ввода логина
                        password.TextFieldPasswordComposable() // отрисовка поля для ввода пароля
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
                            buttonSingin.MyButtonComposable() // отрисовка кнопки входа

                        }
                        Column(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(0.7f)
                                .height(60.dp)
                        ) {
                            buttonSingup.MyButtonComposable() // отрисовка кнопки регистрации
                        }

                    }

                }
            }
        }
    }

    fun AuthenticateUser(nick:String, pas:String, context: Context){
        lifecycleScope.launch(Dispatchers.IO) { // запуск потока IO
            val api = APIService.api.Authenticate(Auth(nick, pas)) // запрос к серверу
            withContext(Dispatchers.Main){ // выполнение кода в main потоке
                if(api.status){ // проверка результата выполнения запроса
                    AddUser(api, pas) // запись пользователя
                    context.startActivity(Intent(context, FunctionalMenu::class.java)) // открытие новой активности
                }
            }
        }
    }

    @Composable
    fun SplashScreen(navController: NavController) {
        val scale = remember {
            androidx.compose.animation.core.Animatable(2f)
        }

        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 2.3f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            navController.popBackStack()
            navController.navigate("main_screen")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.lipi_logo3),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value)
            )
        }
    }

}

