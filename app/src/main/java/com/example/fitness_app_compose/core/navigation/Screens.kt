package com.example.fitness_app_compose.core.navigation

import kotlinx.serialization.Serializable

// com/example/myapp/core/navigation/Screen.kt
//TODO: en iyi yöntem aşşağıda
// https://aistudio.google.com/prompts/189t4_8GGotss_zRXLBSH-KJaT8QUw5ih

@Serializable
sealed interface Screens {
    // Argümanı olmayan basit bir ekran için 'object' kullanmak yeterlidir.
    @Serializable
    object Home : Screens

    @Serializable
    object Register : Screens
    @Serializable
    object Login : Screens

    // Argüman alacak bir ekran için 'data class' kullanmak daha esnektir.
    // Bu sayede argümanları daha güvenli bir şekilde yönetebiliriz.
    @Serializable
    data class ProductDetail(val productId: String) : Screens
//    @Serializable
//    data class ProductDetail(val product: Product) : Screens
}
//@Parcelize // Kotlin derleyicisinin Parcelable metotlarını otomatik oluşturmasını sağlar.
//data class Product(
//    val id: String,
//    val name: String,
//    val description: String,
//    val price: Double
//) : Parcelable