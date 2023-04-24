package com.example.vidoechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vidoechat.ClassesForRendering.ButtonComposable
import com.example.vidoechat.ClassesForRendering.TextBoxComposable
import com.example.vidoechat.ClassesForRendering.TextFieldPasswordComposable
import com.example.vidoechat.LogicFun.OpenActivity
import com.example.vidoechat.ui.theme.BackColor
import com.example.vidoechat.ui.theme.TextBoxColor
import kotlinx.coroutines.delay


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
        NavHost(
            navController = navController,
            startDestination = "splash_screen"
        ) {
            composable("splash_screen") {
                SplashScreen(navController = navController)
            }
            // Main Screen
            composable("main_screen") {

                val password = TextFieldPasswordComposable()
                password.PlaceholderEdition("Password")
                password.BeforeTextEdition("Password:")
                val loginBox = TextBoxComposable()
                loginBox.PlaceholderEdition("Login")
                loginBox.BeforeTextEdition("Login:")
                val buttonSingup =
                    ButtonComposable { OpenActivity(context, Intent(context, SingUP::class.java)) }
                buttonSingup.NameEdition("Sing up")
                val buttonSingin =
                    ButtonComposable {
                        OpenActivity(
                            context,
                            Intent(context, FunctionalMenu::class.java)
                        )
                    }
                buttonSingin.NameEdition("Sing in")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BackColor)
                        .verticalScroll(rememberScrollState()),
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
    }

    @Composable
    fun SplashScreen(navController: NavController) {
        val scale = remember {
            androidx.compose.animation.core.Animatable(0f)
        }

        // AnimationEffect
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            delay(3000L)
            navController.navigate("main_screen")
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.lipi_logo3),
                contentDescription = "Logo",
                modifier = Modifier.scale(3f)
            )
        }
    }

}

