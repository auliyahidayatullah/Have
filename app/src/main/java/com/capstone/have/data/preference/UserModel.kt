package com.capstone.have.data.preference

data class UserModel(
    val username: String,
    val token: String,
    val isLogin: Boolean = false
)