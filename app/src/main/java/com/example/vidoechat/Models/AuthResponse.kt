package com.example.vidoechat.Models

data class AuthResponse(
    val email: String,
    val id_image: Int,
    val name: String,
    val nickname: String,
    val status: Boolean,
    val surname: String
)