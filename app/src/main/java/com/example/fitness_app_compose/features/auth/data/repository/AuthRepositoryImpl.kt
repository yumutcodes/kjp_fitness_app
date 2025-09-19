package com.example.fitness_app_compose.features.auth.data.repository

import com.example.fitness_app_compose.core.network.ApiService
import com.example.fitness_app_compose.core.auth.LoginRequest
import com.example.fitness_app_compose.core.auth.LoginResponse
import com.example.fitness_app_compose.core.auth.RegisterRequest
import com.example.fitness_app_compose.core.auth.RegisterResponse
import com.example.fitness_app_compose.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: ApiService) : AuthRepository {
    override suspend fun login(request: LoginRequest): Result<LoginResponse> = withContext(Dispatchers.IO) {
        runCatching {
            val resp = api.login(request)
            if (resp.isSuccessful) resp.body() ?: throw Exception("Empty body")
            else throw Exception(resp.errorBody()?.string() ?: "Login failed ${resp.code()}")
        }
    }

    override suspend fun register(request: RegisterRequest): Result<RegisterResponse> = withContext(Dispatchers.IO) {
        runCatching {
            val resp = api.register(request)
            if (resp.isSuccessful) resp.body() ?: throw Exception("Empty body")
            else throw Exception(resp.errorBody()?.string() ?: "Register failed ${resp.code()}")
        }
    }
}