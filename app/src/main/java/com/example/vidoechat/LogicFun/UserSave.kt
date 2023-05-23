package com.example.vidoechat.LogicFun

import com.example.vidoechat.Models.AuthResponse
import com.example.vidoechat.Models.User

var userSave:User = User("string", 1,"string","string","string")

fun AddUser( authSave:AuthResponse){
    userSave.name = authSave.name
    userSave.email= authSave.email
    userSave.nickname = authSave.nickname
    userSave.surname = authSave.surname
    userSave.id_image =authSave.id_image
}