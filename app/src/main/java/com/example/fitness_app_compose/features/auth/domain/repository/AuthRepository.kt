package com.example.fitness_app_compose.features.auth.domain.repository

import com.example.fitness_app_compose.core.auth.LoginRequest
import com.example.fitness_app_compose.core.auth.LoginResponse
import com.example.fitness_app_compose.core.auth.RegisterRequest
import com.example.fitness_app_compose.core.auth.RegisterResponse

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>
    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse>
}