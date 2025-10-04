package com.example.fitness_app_compose.core.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.fitness_app_compose.core.network.ApiService
import com.example.fitness_app_compose.core.network.AuthInterceptor
import com.example.fitness_app_compose.core.utilities.errorParser.ErrorResponse
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
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Module
@InstallIn(SingletonComponent::class) // Available for the entire app lifecycle
object SerializationModule {

    /**
     * Provides a singleton instance of the Moshi JSON library.
     * It's configured with the KotlinJsonAdapterFactory to handle Kotlin classes.
     *
     * @return A configured [Moshi] instance.
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    /**
     * Provides a specific [JsonAdapter] for the [ErrorResponse] class.
     * This adapter depends on the globally available [Moshi] instance, which Hilt will inject.
     *
     * @param moshi The Moshi instance provided by Hilt.
     * @return A [JsonAdapter] for parsing [ErrorResponse].
     */
    @Provides
    @Singleton
    fun provideErrorResponseAdapter(moshi: Moshi): JsonAdapter<ErrorResponse> {
        return moshi.adapter(ErrorResponse::class.java)
    }
}