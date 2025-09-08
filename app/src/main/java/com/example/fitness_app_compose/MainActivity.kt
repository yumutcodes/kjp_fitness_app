package com.example.fitness_app_compose

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitness_app_compose.core.navigation.AppNavigation
import com.example.fitness_app_compose.ui.theme.Fitness_app_composeTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Fitness_app_composeTheme {
                Scaffold(
                    topBar = {TopAppBar(title= {Text("ds")})},
                ) {innerPadding   ->
                AppNavigation(innerPadding)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Fitness_app_composeTheme {
        Scaffold(
            topBar = {TopAppBar(title= {Text("ds")})},
        ) {innerPadding   ->
            AppNavigation(innerPadding)
        }
    }
}