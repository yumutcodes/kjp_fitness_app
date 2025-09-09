package com.example.fitness_app_compose.features.auth.login.presentation

import RegisterUiState
import RegisterViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fitness_app_compose.core.navigation.Screens
import com.example.fitness_app_compose.features.auth.login.presentation.compasables.LoginOutlinedTextField

/**
 * ViewModel'i başlatan ve state'i dinleyen ana Composable.
 * Bu Composable "stateful" (durum sahibi) olarak kabul edilir.
 */
@Composable
fun LoginPage(
    registerViewModel: RegisterViewModel = viewModel(),
    navHostController: NavHostController
) {
    // ViewModel'deki StateFlow'u, Compose'un anlayacağı bir State'e dönüştürüyoruz.
    // 'by' anahtar kelimesi sayesinde 'uiState.value' yerine doğrudan 'uiState' kullanabiliriz.
    val uiState by registerViewModel.uiState.collectAsState()

    // UI'ın kendisini, state'i ve event'leri parametre olarak alan
    // başka bir Composable'a devrediyoruz. Bu, preview ve test için en iyi yöntemdir.
    LoginScreenContent(
        uiState = uiState,
        onNameChanged = registerViewModel::onNameChanged,
        onEmailChanged = registerViewModel::onEmailChanged,
        onPasswordChanged = registerViewModel::onPasswordChanged,// Fonksiyon referansı
        onClearClicked = registerViewModel::clearInput,
        navHostController = navHostController
    )
}

/**
 * Sadece UI'ı çizen "stateless" (durum sahibi olmayan) Composable.
 * Ne yapacağını dışarıdan (parametrelerden) alır.
 */
@Composable
fun LoginScreenContent(
    uiState: RegisterUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    navHostController: NavHostController
) {

    val scrollState = rememberScrollState()
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .imePadding() // Klavye açıldığında otomatik padding ekler
//    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding() // ime Padding klavye açılınca otomatik padding ekler
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginOutlinedTextField(
            value = uiState.emailText,
            onInputChanged = onEmailChanged,
            isValid = uiState.IsEmailValid,
            labelText = "Enter Email",
        )

        LoginOutlinedTextField(
            value = uiState.passwordText,
            onInputChanged = onPasswordChanged,
            isValid = uiState.IsPasswordValid,
            labelText = "Enter Password",
        )
        TextButton(
            modifier = Modifier.align(Alignment.Start),
            contentPadding = PaddingValues(end=16.dp, start = 2.dp),
            onClick = {
                navHostController.navigate(Screens.Register)
            }
        ) {
            Text(
                text = "Register",
                fontSize = 20.sp
            )
        }
        //        OutlinedTextField(
        //            value = uiState.nameText,
        //            onValueChange = onNameChanged, // Kullanıcı yazınca bu event tetiklenir
        //            label = { Text("İsim Giriniz (en az 3 karakter)") },
        //            isError = !uiState.IsNameValid, // Hata durumu state'ten okunur
        //            singleLine = true,
        //            modifier = Modifier.fillMaxWidth()
        //        )
        //
        //        // Eğer giriş geçerli değilse, hata mesajını göster
        //        if (!uiState.IsNameValid) {
        //            Text(
        //                text = "İsim en az 3 karakter olmalıdır.",
        //                color = Color.Red,
        //                modifier = Modifier
        //                    .padding(top = 4.dp)
        //                    .align(Alignment.Start) // Metni sola hizala
        //            )
        //        }
        //   Spacer(modifier = Modifier.height(16.dp))


        // Eğer giriş geçerli değilse, hata mesajını göster

        // Spacer(modifier = Modifier.height(16.dp))


        //   Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onClearClicked

        ) {
            Text("Metni Temizle")
        }

    }
    //}
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    // Preview'da ViewModel olmadan UI'ı test edebiliriz.
    LoginScreenContent(
        uiState = RegisterUiState(),
        onEmailChanged = {},
        onNameChanged = {},
        onPasswordChanged = {},
        onClearClicked = {},
        navHostController = NavHostController(LocalContext.current)
    )
}

@Preview(showBackground = true)
@Composable
fun MyScreenErrorPreview() {
    // Hata durumunu da kolayca test edebiliriz.
    LoginScreenContent(
        uiState = RegisterUiState(nameText = "Ha", IsNameValid = false),
        onEmailChanged = {},
        onNameChanged = {},
        onPasswordChanged = {},
        onClearClicked = {},
        navHostController = NavHostController(LocalContext.current)
    )
}