// src/main/java/com/example/fitness_app_compose/core/di/AppModule.kt
package com.example.fitness_app_compose.core.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.fitness_app_compose.core.network.ApiService
import com.example.fitness_app_compose.core.network.AuthInterceptor
import com.example.fitness_app_compose.features.auth.data.repository.AuthRepositoryImpl
import com.example.fitness_app_compose.features.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.fitness_app_compose.features.auth.domain.session.SessionManager
private const val BASE_URL = "https://your.api.base.url/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton//scope
    fun provideEncryptedSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secure_app_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManagerProvider: dagger.Lazy<SessionManager>): AuthInterceptor {
        // Lazy injection to avoid circular dependencies at creation time.
        return AuthInterceptor(sessionManagerProvider)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideSessionManager(sharedPreferences: SharedPreferences): SessionManager {
        Log.d("Hilt SessionManager", "SessionManager oluşturuldu")
        println("SessionManager oluşturuldu")
        return SessionManager(sharedPreferences)
    }


}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {
    @Binds
    @Singleton // emin değilim Singleton mu olmalı ?
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
