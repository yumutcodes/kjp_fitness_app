package com.example.fitness_app_compose.features.auth.domain.session


import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(private val prefs: SharedPreferences) {
    private val KEY_ACCESS = "KEY_ACCESS"
    private val KEY_REFRESH = "KEY_REFRESH"

    fun saveTokens(access: String, refresh: String) {
        prefs.edit().putString(KEY_ACCESS, access).putString(KEY_REFRESH, refresh).apply()
    }

    fun getAccessToken(): String? = prefs.getString(KEY_ACCESS, null)
    fun getRefreshToken(): String? = prefs.getString(KEY_REFRESH, null)
    fun clear() = prefs.edit().clear().apply()
}