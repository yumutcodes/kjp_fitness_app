import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * ViewModel'i başlatan ve state'i dinleyen ana Composable.
 * Bu Composable "stateful" (durum sahibi) olarak kabul edilir.
 */
@Composable
fun RegisterPage(myViewModel: MyViewModel = viewModel()) {
    // ViewModel'deki StateFlow'u, Compose'un anlayacağı bir State'e dönüştürüyoruz.
    // 'by' anahtar kelimesi sayesinde 'uiState.value' yerine doğrudan 'uiState' kullanabiliriz.
    val uiState by myViewModel.uiState.collectAsState()

    // UI'ın kendisini, state'i ve event'leri parametre olarak alan
    // başka bir Composable'a devrediyoruz. Bu, preview ve test için en iyi yöntemdir.
    MyScreenContent(
        uiState = uiState,
        onNameChanged = myViewModel::onNameChanged,
        onEmailChanged = myViewModel::onEmailChanged,
        onPasswordChanged = myViewModel::onPasswordChanged,// Fonksiyon referansı
        onClearClicked = myViewModel::clearInput
    )
}

/**
 * Sadece UI'ı çizen "stateless" (durum sahibi olmayan) Composable.
 * Ne yapacağını dışarıdan (parametrelerden) alır.
 */
@Composable
fun MyScreenContent(
    uiState: MyScreenUiState,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClearClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = uiState.nameText,
            onValueChange = onNameChanged, // Kullanıcı yazınca bu event tetiklenir
            label = { Text("İsim Giriniz (en az 3 karakter)") },
            isError = !uiState.IsNameValid, // Hata durumu state'ten okunur
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Eğer giriş geçerli değilse, hata mesajını göster
        if (!uiState.IsNameValid) {
            Text(
                text = "İsim en az 3 karakter olmalıdır.",
                color = Color.Red,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start) // Metni sola hizala
            )
        }
     //   Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = uiState.emailText,
            onValueChange = onEmailChanged, // Kullanıcı yazınca bu event tetiklenir
            label = { Text("İsim Giriniz (en az 3 karakter)") },
            isError = !uiState.IsEmailValid, // Hata durumu state'ten okunur
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top=16.dp)
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
       // Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(

            value = uiState.passwordText,
            onValueChange = onPasswordChanged, // Kullanıcı yazınca bu event tetiklenir
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

     //   Spacer(modifier = Modifier.height(16.dp))

        Button(
modifier = Modifier.padding(top=16.dp)
            ,onClick = onClearClicked) {

            Text("Metni Temizle")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenPreview() {
    // Preview'da ViewModel olmadan UI'ı test edebiliriz.
    MyScreenContent(
        uiState = MyScreenUiState(),
        onEmailChanged = {},
        onNameChanged = {},
        onPasswordChanged = {},
        onClearClicked = {},
    )
}

@Preview(showBackground = true)
@Composable
fun MyScreenErrorPreview() {
    // Hata durumunu da kolayca test edebiliriz.
    MyScreenContent(
        uiState = MyScreenUiState(nameText = "Ha", IsNameValid = false),
        onEmailChanged = {},
        onNameChanged = {},
        onPasswordChanged = {},
        onClearClicked = {},
    )
}