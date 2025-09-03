package com.example.fitness_app_compose.features.auth.presentatilon.Compasables

import MyScreenUiState
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
fun NameOutlinedTextField(
    uiState: MyScreenUiState,
    onInputChanged: (String) -> Unit,
){
    Column {
        OutlinedTextField(

            value = uiState.passwordText,
            onValueChange = onInputChanged, // Kullanıcı yazınca bu event tetiklenir
            label =  {Text("İsim Giriniz (en az 3 karakter)")} ,
            isError = !uiState.IsPasswordValid, // Hata durumu state'ten okunur
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )

        // Eğer giriş geçerli değilse, hata mesajını göster
        if (!uiState.IsPasswordValid) {
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