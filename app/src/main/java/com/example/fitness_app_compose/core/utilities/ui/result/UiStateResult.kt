package com.example.fitness_app_compose.core.utilities.ui.result

import com.example.fitness_app_compose.core.utilities.ui.error.AppError


sealed class UiState<out T, out E : AppError> {
    data object Loading : UiState<Nothing, Nothing>()
    data class Success<out T>(val data: T) : UiState<T, Nothing>()
    data class Error<out E : AppError>(val error: E) : UiState<Nothing, E>()
}