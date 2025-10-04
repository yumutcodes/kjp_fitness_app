package com.example.fitness_app_compose.core.utilities.resource

sealed interface Resource<out T> {

    /**
     * Initial state before any operation has started.
     */
    data object Idle : Resource<Nothing>

    /**
     * Operation is in progress.
     */
    data object Loading : Resource<Nothing>

    /**
     * Operation completed successfully with data.
     */
    data class Success<T>(val data: T) : Resource<T>

    /**
     * Operation failed with an exception.
     */
    data class Error(val exception: Exception) : Resource<Nothing>
}

// Extension properties for easier type checking
val <T> Resource<T>.isIdle: Boolean get() = this is Resource.Idle
val <T> Resource<T>.isLoading: Boolean get() = this is Resource.Loading
val <T> Resource<T>.isSuccess: Boolean get() = this is Resource.Success
val <T> Resource<T>.isError: Boolean get() = this is Resource.Error

// Extension functions for safe data access
fun <T> Resource<T>.getOrNull(): T? = (this as? Resource.Success)?.data
fun <T> Resource<T>.errorOrNull(): Exception? = (this as? Resource.Error)?.exception

// Extension function for handling all states
inline fun <T> Resource<T>.onIdle(action: () -> Unit): Resource<T> {
    if (this is Resource.Idle) action()
    return this
}

inline fun <T> Resource<T>.onLoading(action: () -> Unit): Resource<T> {
    if (this is Resource.Loading) action()
    return this
}

inline fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) action(data)
    return this
}

inline fun <T> Resource<T>.onError(action: (Exception) -> Unit): Resource<T> {
    if (this is Resource.Error) action(exception)
    return this
}