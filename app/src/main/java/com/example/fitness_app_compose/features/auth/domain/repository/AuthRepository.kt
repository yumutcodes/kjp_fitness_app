package com.example.fitness_app_compose.features.auth.domain.repository

import com.example.fitness_app_compose.features.auth.data.model.*
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse>
    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse>
}