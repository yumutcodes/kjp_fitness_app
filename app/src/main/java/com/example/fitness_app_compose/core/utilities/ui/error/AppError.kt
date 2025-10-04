package com.example.fitness_app_compose.core.utilities.ui.error

import com.example.fitness_app_compose.R
import com.example.fitness_app_compose.core.utilities.ui.uiText.UiText

sealed class AppError(open val message: UiText) {
   data class Api(override val message: UiText) : AppError(message)
    data class Unauthorized(
        override val message: UiText = UiText.StringResource(R.string.error_unauthorized)
    ) : AppError(message)

    data class Network(
        override val message: UiText = UiText.StringResource(R.string.error_network)
    ) : AppError(message)

    data class Unknown(
        override val message: UiText = UiText.StringResource(R.string.error_unknown)
    ) : AppError(message)
    data class Server(
        val code: Int,
        override val message: UiText = UiText.StringResource(R.string.error_server)
    ) : AppError(message)
}
