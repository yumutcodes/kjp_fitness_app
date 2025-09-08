import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Kullanıcı arayüzünün anlık durumunu temsil eden veri sınıfı.
 * Bu yapı, state'i tek bir yerden yönetmeyi ve gelecekte yeni özellikler
 * eklemeyi kolaylaştırır.
 */
data class RegisterUiState(
    val nameText: String = "",
    val IsNameValid: Boolean = true,
    val emailText: String = "",
    val IsEmailValid: Boolean = true,
    val passwordText: String = "",
    val IsPasswordValid: Boolean = true
)

/**
 * MyScreen'in iş mantığını yöneten ViewModel.
 */
class RegisterViewModel : ViewModel() {

    // Sadece ViewModel içerisinden değiştirilebilen, özel (private) MutableStateFlow.
    private val _uiState = MutableStateFlow(RegisterUiState())

    // UI tarafından gözlemlenecek olan, dışarıya açık ve sadece okunabilir StateFlow.
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    /**
     * TextField'daki metin her değiştiğinde UI tarafından çağrılan fonksiyon.
     */
    fun onNameChanged(newText: String) {
        // State'i güncellemek için en güvenli ve modern yöntem 'update' bloğudur.
        _uiState.update { currentState ->
            currentState.copy(
                nameText = newText,
                IsNameValid = validateInput(newText)
            )
        }
    }

    fun onEmailChanged(newText: String) {
        // State'i güncellemek için en güvenli ve modern yöntem 'update' bloğudur.
        _uiState.update { currentState ->
            currentState.copy(
                emailText = newText,
                IsEmailValid = validateInput(newText)
            )
        }
    }

    fun onPasswordChanged(newText: String) {
        // State'i güncellemek için en güvenli ve modern yöntem 'update' bloğudur.
        _uiState.update { currentState ->
            currentState.copy(
                passwordText = newText,
                IsPasswordValid = validateInput(newText)
            )
        }
    }

    /**
     * Metin alanını temizlemek için kullanılan fonksiyon.
     */
    fun clearInput() {
        _uiState.update { it.copy(nameText = "",
            emailText = "",
            passwordText = "",
            IsNameValid = true,
            IsEmailValid = true,
            IsPasswordValid = true

            ) }
    }

    /**
     * Basit bir doğrulama kuralı.
     * Metin boş değilse en az 3 karakter uzunluğunda olmalıdır.
     */
    private fun validateInput(input: String): Boolean {
        // Metin boşken hata gösterme, yazmaya başlayınca kontrol et.
        return input.length >= 3
    }
}