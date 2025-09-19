// src/main/java/com/example/fitness_app_compose/core/network/ApiService.kt
package com.example.fitness_app_compose.core.network

import com.example.fitness_app_compose.core.auth.LoginRequest
import com.example.fitness_app_compose.core.auth.LoginResponse
import com.example.fitness_app_compose.core.auth.RegisterRequest
import com.example.fitness_app_compose.core.auth.RegisterResponse
import com.example.fitness_app_compose.core.auth.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<LoginResponse>
}
