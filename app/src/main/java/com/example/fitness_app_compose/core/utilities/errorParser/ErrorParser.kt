package com.example.fitness_app_compose.core.utilities.errorParser

import com.example.fitness_app_compose.R
import com.example.fitness_app_compose.core.utilities.AppError.AppError
import com.example.fitness_app_compose.core.utilities.ui.uiText.UiText

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Error response from API.
 */
data class ErrorResponse(
    val message: String?,
    val errors: Map<String, List<String>>? = null
)

/**
 * Parses exceptions into AppError objects.
 */
@Singleton
class ErrorParser @Inject constructor( // <-- Hilt'e bu sınıfı nasıl oluşturacağını söylüyoruz
    private val errorAdapter: JsonAdapter<ErrorResponse> // <-- Bağımlılığı dışarıdan alıyoruz
){



    fun parse(throwable: Throwable): AppError {
        return when (throwable) {
            is HttpException -> parseHttpException(throwable)
            is UnknownHostException -> AppError.Network.NoInternet
            is SocketTimeoutException -> AppError.Network.Timeout
            is IOException -> AppError.Network.ConnectionFailed
            else -> {
                Timber.e(throwable, "Unknown error occurred")
                AppError.Unknown(throwable = throwable)
            }
        }
    }

    private fun parseHttpException(exception: HttpException): AppError {
        val code = exception.code()
        val errorBody = try {
            exception.response()?.errorBody()?.string()
        } catch (e: Exception) {
            null
        }

        val errorResponse = try {
            errorBody?.let { errorAdapter.fromJson(it) }
        } catch (e: Exception) {
            null
        }

        val message = errorResponse?.message ?: exception.message() ?: "Unknown error"
        val validationErrors = errorResponse?.errors
        return when (val code = exception.code()) {
            401 -> AppError.Auth.Unauthorized
            403 -> AppError.Auth.InvalidCredentials
            422 -> AppError.Http( // Example for unprocessable entity
                code = code,
                message = UiText.DynamicString(message ?: "Validation failed"),
            )
            in 400..599 -> AppError.Http(
                code = code,
                message = UiText.DynamicString(message ?: "An error occurred")
            )
            else -> AppError.Unknown(
                message = UiText.StringResource(R.string.error_unknown), // Using string resource
                throwable = exception
            )
        }
    }
    fun ThrowableToAppError(throwable: Throwable): AppError {
        return parse(throwable  )
    }

}

