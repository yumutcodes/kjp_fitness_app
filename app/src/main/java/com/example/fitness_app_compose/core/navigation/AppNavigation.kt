package com.example.fitness_app_compose.core.navigation

import RegisterPage
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness_app_compose.features.auth.login.presentation.LoginPage

// com/example/myapp/core/navigation/AppNavigation.kt
//TODO: en iyi yöntem aşşağıda
// https://aistudio.google.com/prompts/189t4_8GGotss_zRXLBSH-KJaT8QUw5ih
@Composable
fun AppNavigation() {
    // NavController'ı oluştur ve recomposition'lar boyunca durumunu hatırla.
    val navController = rememberNavController()

    // NavHost, navigasyon grafiğini kurar.s
    NavHost(

        navController = navController,
        startDestination = Screens.Register, // Uygulama açıldığında gösterilecek ilk ekran
        enterTransition = { EnterTransition.None },

        // Bir ekrandan çıkarken animasyon olmasın
        exitTransition = { ExitTransition.None },

        // Geri tuşuyla bir ekrana dönerken animasyon olmasın
        popEnterTransition = { EnterTransition.None },

        // Geri tuşuyla bir ekrandan çıkarken animasyon olmasın
        popExitTransition = { ExitTransition.None }

    ) {
        composable<Screens.Login> {
            // it -> NavBackStackEntry: Bu rotaya ait bilgileri içerir.
            LoginPage(
                navHostController = navController
            )
        }
        composable<Screens.Register> {
            // it -> NavBackStackEntry: Bu rotaya ait bilgileri içerir.
            RegisterPage(
navHostController = navController
            )
        }

    }
}
//örnek kod
//composable<Screens.Home> {
//    HomeScreen(
//        onNavigateToDetails = { productId ->
//            // ProductDetail ekranına giderken bir nesne oluşturup gönderiyoruz.
//            // Artık manuel string birleştirme yok!
//            navController.navigate(Screens.ProductDetail(productId = productId))
//        }
//    )
//}
//
//// Argüman alan ekran
//composable<Screens.ProductDetail> { backStackEntry ->
//    // Gelen argümanları tip-güvenli bir şekilde alıyoruz.
//    val productDetailScreen: Screens.ProductDetail = backStackEntry.toRoute()
//    val productId = productDetailScreen.productId
//
//    ProductDetailScreen(productId = productId)
//}