package com.example.hotelbooking.ui.login

import com.example.hotelbooking.data.model.User

sealed class LoginState {
    object Idle                               : LoginState()
    object Loading                            : LoginState()
    data class Success(val user: User)        : LoginState()
    data class Error(val message: String)     : LoginState()
    data class ValidationError(val message: String) : LoginState()
}
