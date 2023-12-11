package com.dicoding.mystoryapp.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val login: Boolean = false
)