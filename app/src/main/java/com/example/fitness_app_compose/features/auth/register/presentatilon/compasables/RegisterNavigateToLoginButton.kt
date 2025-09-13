package com.example.fitness_app_compose.features.auth.register.presentatilon.compasables

import RegisterUiState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fitness_app_compose.core.navigation.Screens

@Composable
fun RegisterLoginNavigateButton(
    uiState: RegisterUiState,
    navHostController: NavHostController,
    boxmodifier: Modifier = Modifier.Companion,
    textButtonModifier: Modifier = Modifier.Companion
)
{
when (uiState.registerState) {
    RegisterState.Loading -> {
        Box(
            modifier = boxmodifier,
        )
    }
    else -> {
        TextButton(
            modifier = textButtonModifier,
            contentPadding = PaddingValues(end = 16.dp, start = 2.dp),
            onClick = {
                navHostController.navigate(Screens.Login)
            }
        ) {
            Text(
                text = "Login",
                fontSize = 20.sp
            )
        }
    }

}
}