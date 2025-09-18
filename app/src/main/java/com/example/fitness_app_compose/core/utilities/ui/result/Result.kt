package com.example.fitness_app_compose.core.utilities.ui.result

import com.example.fitness_app_compose.core.utilities.ui.error.AppError


sealed class Result<out T, out E : AppError> {
    data object Loading : Result<Nothing, Nothing>()
    data class Success<out T>(val data: T) : Result<T, Nothing>()
    data class Error<out E : AppError>(val error: E) : Result<Nothing, E>()
}