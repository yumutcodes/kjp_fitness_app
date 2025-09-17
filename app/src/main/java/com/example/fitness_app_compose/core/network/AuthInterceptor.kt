package com.example.fitness_app_compose.core.network



import com.example.fitness_app_compose.features.auth.domain.session.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManagerLazy: dagger.Lazy<SessionManager>
) : Interceptor {
    // Bu interceptor, eğer token varsa Authorization header ekler.
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // SessionManager'ın elde edilmesi sırasında hata/çakma riskine karşı try/catch
        val token = try {
            sessionManagerLazy.get().getAccessToken()
        } catch (t: Throwable) {
            null
        }

        val newRequest = if (!token.isNullOrBlank()) {
            // header kullanıyoruz; addHeader yerine header ile varsa üzerine yazıyoruz
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        return chain.proceed(newRequest)
    }
}
