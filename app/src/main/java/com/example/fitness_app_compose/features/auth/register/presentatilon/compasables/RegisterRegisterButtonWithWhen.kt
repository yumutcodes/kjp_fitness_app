package com.example.fitness_app_compose.features.auth.register.presentatilon.compasables

import RegisterUiState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitness_app_compose.core.utilities.ui.theme.AppTheme
@Composable
fun RegisterRegisterButtonWithWhen(
    uiState: RegisterUiState,
    onRegisterClicked: () -> Unit,
)
{
when (uiState.registerState) {
    RegisterState.Loading -> {
        CircularProgressIndicator()
    }

    else -> {
        RegisterButton(
            modifier = Modifier
                .size(width = 200.dp, height = 70.dp)
                .padding(top = 16.dp),
            onRegisterClicked = onRegisterClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.appColors.accent
            )
        )

    }
}
}
@Composable
private fun RegisterButton(

    modifier: Modifier = Modifier,
    onRegisterClicked: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors()

)
{

    Button(
        modifier =modifier ,
        onClick = onRegisterClicked,
//background mavi olsun
        colors = colors

    ) {
        Text("Register",
            fontSize=20.sp
        )
    }
}