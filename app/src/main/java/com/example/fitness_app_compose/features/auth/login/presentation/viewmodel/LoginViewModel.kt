package com.example.fitness_app_compose.features.auth.login.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.fitness_app_compose.features.auth.domain.session.SessionManager
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app_compose.core.auth.LoginRequest
import com.example.fitness_app_compose.features.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

/**
 * Kullanıcı arayüzünün anlık durumunu temsil eden veri sınıfı.
 * Bu yapı, state'i tek bir yerden yönetmeyi ve gelecekte yeni özellikler
 * eklemeyi kolaylaştırır.
 */
@Serializable
data class LoginUiState(
    val emailText: String = "",
    val IsEmailValid: Boolean = true,
    val passwordText: String = "",
    val IsPasswordValid: Boolean = true
)

/**
 * MyScreen'in iş mantığını yöneten ViewModel.
 */
//SavedStateHandle dependencysi navigation ile gönderilen verileri okumaya yarar
//hiltviewModel bu dependencyi otomatik olarak ekler.
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val sessionManager: SessionManager,
    savedStateHandle: SavedStateHandle
):
    ViewModel()
{
    private val age: Int = savedStateHandle.get<Int>("age") ?: 0
    private val name: String = savedStateHandle.get<String>("name") ?: "Misafir"
    // Sadece ViewModel içerisinden değiştirilebilen, özel (private) MutableStateFlow.
    private val _uiState = MutableStateFlow(LoginUiState())

    // UI tarafından gözlemlenecek olan, dışarıya açık ve sadece okunabilir StateFlow.
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * TextField'daki metin her değiştiğinde UI tarafından çağrılan fonksiyon.
     */


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
        _uiState.update { it.copy(
            emailText = "",
            passwordText = "",

            IsEmailValid = true,
            IsPasswordValid = true

        ) }
    }
    fun login() {
        val email = uiState.value.emailText.trim()
        val password = uiState.value.passwordText

        // Lokal doğrulama
        val emailValid = validateInput(email)
        val passwordValid = validateInput(password)

        // Eğer doğrulama başarısızsa state'i güncelle ve çık
        if (!emailValid || !passwordValid) {
            _uiState.update {
                it.copy(
                    IsEmailValid = emailValid,
                    IsPasswordValid = passwordValid,

                )
            }
            return
        }
        // Network çağrısı
        viewModelScope.launch {
          //  _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }

            try {
                val result = repo.login(LoginRequest(email = email, pass = password))

                result.onSuccess { response ->
                    // Token'ları güvenli bir şekilde kaydet
                    sessionManager.saveTokens(response.accessToken,
                        response.refreshToken)

                    // Başarı durumunu bildir
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            errorMessage = null,
//                            isSuccess = true
//                        )
//                    }
                }.onFailure { throwable ->
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            errorMessage = throwable.message ?: "Giriş başarısız",
//                            isSuccess = false
//                        )
//                    }
                }
            } catch (t: Throwable) {
                // Genel beklenmeyen hatalar
//                _uiState.update {
//                    it.copy(
//                        isLoading = false,
//                        errorMessage = t.message ?: "Bilinmeyen hata",
//                        isSuccess = false
//                    )
//                }
            }
        }
    }




    }



    /**
     * Basit bir doğrulama kuralı.
     * Metin boş değilse en az 3 karakter uzunluğunda olmalıdır.
     */
    private fun validateInput(input: String): Boolean {
        // Metin boşken hata gösterme, yazmaya başlayınca kontrol et.
        return input.length >= 3
    }
