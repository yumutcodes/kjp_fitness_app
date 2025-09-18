package com.example.fitness_app_compose.core.utilities.ui.error

import com.example.fitness_app_compose.core.utilities.ui.uiText.UiText

sealed class AppError(val message: UiText) {
    class Api(message: UiText) : AppError(message)
    object Unauthorized : AppError(UiText.DynamicString("Unauthorized access. Please login again."))
    class Network(message: UiText =UiText.DynamicString("Please check your internet connection.")) : AppError(message)
    class Unknown(message: UiText = UiText.DynamicString("An unknown error occurred.")) : AppError(message)
}
