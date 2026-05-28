package com.example.hotelbooking.ui.register

sealed class RegisterState {
    object Idle                                     : RegisterState()
    object Loading                                  : RegisterState()
    object Success                                  : RegisterState()
    data class Error(val message: String)           : RegisterState()
    data class ValidationError(val message: String) : RegisterState()
}
