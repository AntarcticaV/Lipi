package com.example.vidoechat.LogicFun

fun ChackPassword(password : String, passwordConfirm : String) : Boolean{
    if (password == passwordConfirm)
        return true
    return false
}