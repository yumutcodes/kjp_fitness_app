package com.example.fitness_app_compose.core.navigation

import kotlinx.serialization.Serializable

// com/example/myapp/core/navigation/Screen.kt

@Serializable
sealed class Screens(val route: String) {
    // Argümanı olmayan basit bir ekran için 'object' kullanmak yeterlidir.
    @Serializable
    object Home : Screens("home_screen")

    @Serializable
    object Register : Screens("register")
    @Serializable
    object Login : Screens("login")

    // Argüman alacak bir ekran için 'data class' kullanmak daha esnektir.
    // Bu sayede argümanları daha güvenli bir şekilde yönetebiliriz.
    @Serializable
    object ProductDetail : Screens("product_detail_screen/{productId}") {
        // Argümanlı rotayı kolayca oluşturmak için bir yardımcı fonksiyon.
        fun createRoute(productId: String): String {
            return "product_detail_screen/$productId"
        }
    }
}