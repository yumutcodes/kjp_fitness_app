package com.example.fitness_app_compose.features.auth.data.model


data class LoginRequest(val email: String, val pass: String)
data class RegisterRequest(val name: String, val email: String, val pass: String)
data class RefreshTokenRequest(val refreshToken: String)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val message: String? = null
)

data class RegisterResponse(val message: String?)