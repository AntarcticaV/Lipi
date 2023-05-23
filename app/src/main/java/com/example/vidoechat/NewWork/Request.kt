package com.example.vidoechat.NewWork

import com.example.vidoechat.Models.AuthResponse
import com.example.vidoechat.Models.Auth
import com.example.vidoechat.Models.Registration
import retrofit2.http.Body
import retrofit2.http.POST

interface Request {
    @POST("user/authenticate_user")
    suspend fun Authenticate(@Body user:Auth):AuthResponse

    @POST("user/registration_user")
    suspend fun Registration(@Body user:Registration):AuthResponse

}