// src/main/java/com/example/fitness_app_compose/core/network/ApiService.kt
package com.example.fitness_app_compose.core.network

import com.example.fitness_app_compose.features.auth.data.model.LoginRequest
import com.example.fitness_app_compose.features.auth.data.model.LoginResponse
import com.example.fitness_app_compose.features.auth.data.model.RegisterRequest
import com.example.fitness_app_compose.features.auth.data.model.RegisterResponse
import com.example.fitness_app_compose.features.auth.data.model.RefreshTokenRequest
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
