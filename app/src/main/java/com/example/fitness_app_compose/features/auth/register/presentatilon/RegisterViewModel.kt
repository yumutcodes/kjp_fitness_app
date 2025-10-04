package com.example.fitness_app_compose.features.auth.register.presentatilon

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_app_compose.features.auth.domain.repository.AuthRepository
import com.example.fitness_app_compose.features.auth.domain.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Kullanıcı arayüzünün anlık durumunu temsil eden veri sınıfı.
 * Bu yapı, state'i tek bir yerden yönetmeyi ve gelecekte yeni özellikler
 * eklemeyi kolaylaştırır.
 */

/**
 * MyScreen'in iş mantığını yöneten ViewModel.
 */
@HiltViewModel
    class RegisterViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val sessionManager: SessionManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel Deleted", "RegisterViewModel Deleted")
    }

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
    fun onRegisterClicked() {
        viewModelScope.launch {
            // Set the state to Loading
            _uiState.update { currentState ->
                currentState.copy(registerState = RegisterState.Loading)
            }

            // Wait for 3 seconds
            delay(3000)

            // Set the state back to Normal
            _uiState.update { currentState ->
                currentState.copy(registerState = RegisterState.Idle)
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
}