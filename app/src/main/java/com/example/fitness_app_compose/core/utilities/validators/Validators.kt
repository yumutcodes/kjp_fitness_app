package com.example.fitness_app_compose.core.utilities.validators

import android.util.Patterns

object Validators {
    enum class PasswordStrength {
        WEAK, MEDIUM, STRONG
    }
    private const val MIN_PASSWORD_LENGTH = 6
    private const val MIN_NAME_LENGTH = 2

    // FIX: Trim before validating
    fun isValidEmail(email: String): Boolean {
        val trimmed = email.trim()
        return trimmed.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(trimmed).matches()
    }

    fun isPasswordLongEnough(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }

    // FIX: MIN_NAME_LENGTH = 2 fails for some cultures (Chinese single-character names)
    // Consider removing length check or make it 1
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length >= MIN_NAME_LENGTH
    }

    fun getPasswordStrength(password: String): PasswordStrength {
        if (password.length < MIN_PASSWORD_LENGTH) return PasswordStrength.WEAK

        var score = 0
        if (password.length >= 8) score++           // Length
        if (password.any { it.isUpperCase() }) score++  // Uppercase
        if (password.any { it.isLowerCase() }) score++  // Lowercase
        if (password.any { it.isDigit() }) score++      // Numbers
        if (password.any { !it.isLetterOrDigit() }) score++ // Special chars

        return when {
            score <= 2 -> PasswordStrength.WEAK    // 0-2 criteria
            score <= 3 -> PasswordStrength.MEDIUM  // 3 criteria
            else -> PasswordStrength.STRONG        // 4-5 criteria
        }
    }

    fun sanitizeInput(input: String): String {
        return input.trim().replace(Regex("\\s+"), " ")
    }
}