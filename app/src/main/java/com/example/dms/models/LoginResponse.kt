package com.example.dms.models

data class LoginResponse(
    val user: User?,
    val token: String?
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String?
)
