package com.example.fitness_app_compose.features.auth.register.presentatilon


sealed interface RegisterState {
    object Idle : RegisterState // Başlangıç durumu, hiçbir işlem yapılmadı
    object Loading : RegisterState // İşlem devam ediyor
    object Success : RegisterState // İşlem başarılı
    data class Error(val message: String) : RegisterState // İşlemde hata var
}
data class RegisterUiState(
    val nameText: String = "",
    val IsNameValid: Boolean = true,
    val emailText: String = "",
    val IsEmailValid: Boolean = true,
    val passwordText: String = "",
    val IsPasswordValid: Boolean = true,
    val registerState: RegisterState = RegisterState.Idle


)