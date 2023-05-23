package com.example.vidoechat.NewWork

import com.example.vidoechat.Setting.host
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {
    val retrofit = Retrofit.Builder()
        .baseUrl(host)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(Request::class.java)
}