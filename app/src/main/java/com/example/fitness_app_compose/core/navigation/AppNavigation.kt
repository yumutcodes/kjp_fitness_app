package com.example.fitness_app_compose.core.navigation

import RegisterPage
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// com/example/myapp/core/navigation/AppNavigation.kt

@Composable
fun AppNavigation(innerpadding: PaddingValues) {
    // NavController'ı oluştur ve recomposition'lar boyunca durumunu hatırla.
    val navController = rememberNavController()

    // NavHost, navigasyon grafiğini kurar.
    NavHost(
        navController = navController,
        startDestination = Screens.Register.route // Uygulama açıldığında gösterilecek ilk ekran
    ) {
        composable(route = Screens.Register.route) {
            // it -> NavBackStackEntry: Bu rotaya ait bilgileri içerir.
            RegisterPage(

            )

            // Ana ekranın rotasını ve Composable'ını tanımla
            /*   composable(route = Screen.Home.route) {
            // it -> NavBackStackEntry: Bu rotaya ait bilgileri içerir.
            HomeScreen(
                onNavigateToDetail = { productId ->
                    // Detay ekranına gitmek için navigate fonksiyonunu çağırıyoruz.
                    // Rota oluşturma fonksiyonumuzu kullanarak hatasız bir rota elde ediyoruz.
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }*/

            // Ürün detay ekranının rotasını ve Composable'ını tanımla
            /*  composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { // Argümanın adını ve tipini belirtiyoruz.
                type = NavType.StringType
            })
        ) { backStackEntry ->
            // NavHost'tan argümanı alıyoruz. Rota'daki {productId} ile aynı isimde olmalı.
            val productId = backStackEntry.arguments?.getString("productId")

            ProductDetailScreen(
                productId = productId,
                onNavigateBack = {
                    // Geri tuşuna basma işlevini programatik olarak tetikler.
                    navController.popBackStack()
                }
            )
        }
        */
        }
    }
}