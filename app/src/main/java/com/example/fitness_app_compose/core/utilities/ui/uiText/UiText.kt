package com.example.fitness_app_compose.core.utilities.ui.uiText

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
/*
DynamicString is for Strings that does not comes from xml or resources.
StringResource is for Strings that comes from xml or resources.
args in StringResource is for arguments that can be passed to the string.
chat:
https://aistudio.google.com/prompts/1JaXTkBzEgx3Z9c5y7DCLiweRvjIrvuLf
/*
example:
<resources>
    <!-- A simple string with no arguments -->
    <string name="error_title">Error</string>

    <!-- A string with one string argument (%s) -->
    <string name="welcome_message">Welcome back, %s!</string>

    <!-- A string with an integer (%d) and a string (%s) argument -->
    <string name="unread_notifications">You have %d new messages from %s.</string>
</resources>
val welcomeText = UiText.StringResource(R.string.welcome_message, "Alice")
val notificationText = UiText.StringResource(R.string.unread_notifications, 5, "John")

 */
 */
sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    data class StringResource(
        @param:StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText() {
        constructor(@StringRes resId: Int, vararg args: Any) : this(resId, args.toList())
    }
    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(resId, *args.toTypedArray())
        }
    }
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args.toTypedArray())
        }
    }
}