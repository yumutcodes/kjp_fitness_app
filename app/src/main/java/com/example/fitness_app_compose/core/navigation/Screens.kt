package com.example.fitness_app_compose.core.navigation

// com/example/myapp/core/navigation/Screen.kt

sealed class Screens(val route: String) {
    // Argümanı olmayan basit bir ekran için 'object' kullanmak yeterlidir.
    object Home : Screens("home_screen")

    object Register : Screens("register")

    // Argüman alacak bir ekran için 'data class' kullanmak daha esnektir.
    // Bu sayede argümanları daha güvenli bir şekilde yönetebiliriz.
    object ProductDetail : Screens("product_detail_screen/{productId}") {
        // Argümanlı rotayı kolayca oluşturmak için bir yardımcı fonksiyon.
        fun createRoute(productId: String): String {
            return "product_detail_screen/$productId"
        }
    }
}