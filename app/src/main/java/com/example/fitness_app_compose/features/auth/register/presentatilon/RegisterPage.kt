import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface

import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fitness_app_compose.core.utilities.ui.preview.DevicePreviews
import com.example.fitness_app_compose.core.utilities.ui.preview.ThemePreviews
import com.example.fitness_app_compose.core.utilities.ui.theme.AppColors
import com.example.fitness_app_compose.core.utilities.ui.theme.AppTheme
import com.example.fitness_app_compose.core.utilities.ui.theme.Fitness_app_composeTheme
import com.example.fitness_app_compose.features.auth.register.presentatilon.RegisterUiState
import com.example.fitness_app_compose.features.auth.register.presentatilon.RegisterViewModel
import com.example.fitness_app_compose.features.auth.register.presentatilon.compasables.RegisterLoginNavigateButton
import com.example.fitness_app_compose.features.auth.register.presentatilon.compasables.RegisterOutlinedTextField
import com.example.fitness_app_compose.features.auth.register.presentatilon.compasables.RegisterRegisterButtonWithWhen

/**
 * ViewModel'i başlatan ve state'i dinleyen ana Composable.
 * Bu Composable "stateful" (durum sahibi) olarak kabul edilir.
 */
@Composable
fun RegisterPage(
    registerViewModel: RegisterViewModel = hiltViewModel(), navHostController: NavHostController
) {
    DisposableEffect(Unit) {
        onDispose {
            Log.d("RegisterPage Deleted", "RegisterPage Deleted.")
        }
    }
    // ViewModel'deki StateFlow'u, Compose'un anlayacağı bir State'e dönüştürüyoruz.
    // 'by' anahtar kelimesi sayesinde 'uiState.value' yerine doğrudan 'uiState' kullanabiliriz.
    val uiState by registerViewModel.uiState.collectAsState()

    // UI'ın kendisini, state'i ve event'leri parametre olarak alan
    // başka bir Composable'a devrediyoruz. Bu, preview ve test için en iyi yöntemdir.


    MyScreenContent(
        uiState = uiState,
        onNameChanged = registerViewModel::onNameChanged,
        onEmailChanged = registerViewModel::onEmailChanged,
        onPasswordChanged = registerViewModel::onPasswordChanged,// Fonksiyon referansı
        onRegisterClicked = registerViewModel::onRegisterClicked,
        navHostController = navHostController
    )

}


/**
 * Sadece UI'ı çizen "stateless" (durum sahibi olmayan) Composable.
 * Ne yapacağını dışarıdan (parametrelerden) alır.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreenContent(
    uiState: RegisterUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    navHostController: NavHostController
) {

    val scrollState = rememberScrollState()
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .imePadding() // Klavye açıldığında otomatik padding ekler
//    ) {

    Scaffold(
        topBar = { TopAppBar(title = { Text("Register") },

            colors = TopAppBarDefaults.topAppBarColors(
                // TopAppBar'ın arka plan rengini burada belirliyoruz.
                containerColor = AppTheme.appColors.blue700,

                // Başlık rengini de buradan ayarlayabiliriz.
                titleContentColor = Color.White

            )) },
        containerColor = AppTheme.appColors.accent,

    ) { innerPadding ->
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding() // ime Padding klavye açılınca otomatik padding ekler
                    .padding(innerPadding)
                    .verticalScroll(scrollState)

                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RegisterOutlinedTextField(
                    value = uiState.nameText,
                    onInputChanged = { gelenVeri -> onNameChanged(gelenVeri) },
                    isValid = uiState.IsNameValid,
                    labelText = "Enter Name",
                )
                RegisterOutlinedTextField(
                    value = uiState.emailText,
                    onInputChanged = onEmailChanged,
                    isValid = uiState.IsEmailValid,
                    labelText = "Enter Email",
                )

                RegisterOutlinedTextField(
                    value = uiState.passwordText,
                    onInputChanged = onPasswordChanged,
                    isValid = uiState.IsPasswordValid,
                    labelText = "Enter Password",
                )
                RegisterLoginNavigateButton(
                    uiState = uiState,
                    navHostController = navHostController,
                    boxmodifier = Modifier.size(20.dp),
                    textButtonModifier = Modifier.align(Alignment.Start)
                )
                RegisterRegisterButtonWithWhen(
                    uiState = uiState,
                    onRegisterClicked = onRegisterClicked,
                )


            }
        }
    }
    //}
}

@DevicePreviews
@ThemePreviews
@Composable
fun MyScreenPreview() {
    // Preview'da ViewModel olmadan UI'ı test edebiliriz.
    Fitness_app_composeTheme(

    ) {
        MyScreenContent(
            uiState = RegisterUiState(),
            onEmailChanged = {},
            onNameChanged = {},
            onPasswordChanged = {},
            onRegisterClicked = {},
            navHostController = NavHostController(LocalContext.current)
        )
    }
}

