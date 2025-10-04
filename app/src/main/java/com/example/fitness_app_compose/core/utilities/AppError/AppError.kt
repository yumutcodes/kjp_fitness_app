package com.example.fitness_app_compose.core.utilities.AppError

import com.example.fitness_app_compose.R
import com.example.fitness_app_compose.core.utilities.ui.uiText.UiText
/*
sealed interface AppError {
    // Network-level errors (before reaching server)
    data object NoInternet : AppError
    data object Timeout : AppError
    data object ConnectionFailed : AppError

    // HTTP errors (response received)
    data class HttpError(
        val code: Int,
        val message: UiText
    ) : AppError

    // Auth-specific (convenience)
    data object Unauthorized : AppError

    // Client-side errors
    data class Validation(
        val field: String,
        val message: UiText
    ) : AppError

    // Catch-all
    data class Unknown(
        val message: UiText = UiText.StringResource(R.string.error_unknown)
    ) : AppError
}
*/
sealed interface AppError {

    /**
     * Network-level errors (unable to reach server).
     */
    sealed interface Network : AppError {
        data object NoInternet : Network
        data object Timeout : Network
        data object ConnectionFailed : Network
        data class Custom(val message: UiText) : Network
    }

    /**
     * HTTP errors (server responded with error status).
     * @param code HTTP status code (400-599)
     * @param message Error message from server or fallback
     */
    data class Http(
        val code: Int,
        val message: UiText
    ) : AppError {
        val isBadRequest: Boolean get() = code == 400
        val isUnauthorized: Boolean get() = code == 401
        val isForbidden: Boolean get() = code == 403
        val isNotFound: Boolean get() = code == 404
        val isServerError: Boolean get() = code in 500..599
    }

    /**
     * Authentication/authorization errors.
     */
    sealed interface Auth : AppError {
        data object Unauthorized : Auth
        data object TokenExpired : Auth
        data object InvalidCredentials : Auth
        data object SessionExpired : Auth
    }

    /**
     * Client-side validation errors.
     * @param field Field name that failed validation
     * @param message Validation error message
     */
    data class Validation(
        val field: String,
        val message: UiText
    ) : AppError

    /**
     * Unknown or unexpected errors.
     * @param throwable Original exception if available
     * @param message Custom error message
     */
    data class Unknown(
        val message: UiText = UiText.StringResource(R.string.error_unknown),
        val throwable: Throwable? = null
    ) : AppError
}
/**
 * Parse HTTP exception to AppError.
 */
fun parseHttpError(code: Int, message: String? = null): AppError {
    return when (code) {
        401 -> AppError.Auth.Unauthorized
        403 -> AppError.Auth.InvalidCredentials
        in 400..499 -> AppError.Http(
            code = code,
            message = message?.let { UiText.DynamicString(it) }
                ?: UiText.StringResource(R.string.error_client)
        )
        in 500..599 -> AppError.Http(
            code = code,
            message = message?.let { UiText.DynamicString(it) }
                ?: UiText.StringResource(R.string.error_server)
        )
        else -> AppError.Unknown(
            message = message?.let { UiText.DynamicString(it) }
                ?: UiText.StringResource(R.string.error_unknown)
        )
    }
}

// ============================================================================
// Extension Properties for UI Display
// ============================================================================

/**
 * Get user-friendly error message.
 */
val AppError.displayMessage: UiText
    get() = when (this) {
        is AppError.Network.NoInternet -> UiText.StringResource(R.string.error_no_internet)
        is AppError.Network.Timeout -> UiText.StringResource(R.string.error_timeout)
        is AppError.Network.ConnectionFailed -> UiText.StringResource(R.string.error_connection)
        is AppError.Network.Custom -> message
        is AppError.Http -> message
        is AppError.Auth.Unauthorized -> UiText.StringResource(R.string.error_unauthorized)
        is AppError.Auth.TokenExpired -> UiText.StringResource(R.string.error_token_expired)
        is AppError.Auth.InvalidCredentials -> UiText.StringResource(R.string.error_invalid_credentials)
        is AppError.Auth.SessionExpired -> UiText.StringResource(R.string.error_session_expired)
        is AppError.Validation -> message
        is AppError.Unknown -> message
    }

/**
 * Determine if error is retryable.
 */
val AppError.isRetryable: Boolean
    get() = when (this) {
        is AppError.Network -> true
        is AppError.Http -> isServerError
        is AppError.Auth.TokenExpired -> true
        is AppError.Auth.SessionExpired -> true
        else -> false
    }