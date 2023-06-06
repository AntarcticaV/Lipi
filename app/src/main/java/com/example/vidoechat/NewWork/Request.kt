package com.example.vidoechat.NewWork

import com.example.vidoechat.Models.AuthResponse
import com.example.vidoechat.Models.Auth
import com.example.vidoechat.Models.ChechNick
import com.example.vidoechat.Models.ChechPas
import com.example.vidoechat.Models.Registration
import retrofit2.http.Body
import retrofit2.http.POST

interface Request {
    @POST("user/authenticate_user")
    suspend fun Authenticate(@Body user:Auth):AuthResponse

    @POST("user/registration_user")
    suspend fun Registration(@Body user:Registration):AuthResponse

    @POST("user/chench_password")
    suspend fun ChanchPassword(@Body user:ChechPas):AuthResponse

    @POST("user/chench_nickname")
    suspend fun ChenchNickname(@Body user:ChechNick):AuthResponse


}