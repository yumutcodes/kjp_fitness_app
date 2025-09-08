package com.example.fitness_app_compose.features.auth.register.presentatilon.compasables

import RegisterUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmailOutlinedTextField(
    uiState: RegisterUiState,
    onInputChanged: (String) -> Unit,
){
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.emailText,
            onValueChange = onInputChanged, // Kullanıcı yazınca bu event tetiklenir
            label =  {Text("İsim Giriniz (en az 3 karakter)")} ,
            isError = !uiState.IsEmailValid, // Hata durumu state'ten okunur
            singleLine = true,

            )

        // Eğer giriş geçerli değilse, hata mesajını göster
        if (!uiState.IsEmailValid) {
            Text(
                text = "İsim en az 3 karakter olmalıdır.",
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start) // Metni sola hizala
            )
        }
    }
}

