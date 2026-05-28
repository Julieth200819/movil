package com.example.hotelbooking.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbooking.data.repository.AuthRepository
import com.example.hotelbooking.utils.SessionManager
import com.example.hotelbooking.utils.Validators
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login(correo: String, password: String) {
        // 1. Validaciones locales (no se toca la red)
        when {
            !Validators.isNotBlank(correo) ->
                return _loginState.setValue(LoginState.ValidationError("El correo es obligatorio"))
            !Validators.isValidEmail(correo) ->
                return _loginState.setValue(LoginState.ValidationError("Ingresa un correo válido"))
            !Validators.isNotBlank(password) ->
                return _loginState.setValue(LoginState.ValidationError("La contraseña es obligatoria"))
        }

        // 2. Llamada a la API
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val result = repository.login(correo, password)
            result.fold(
                onSuccess = { user ->
                    sessionManager.saveUser(user)
                    _loginState.value = LoginState.Success(user)
                },
                onFailure = { e ->
                    _loginState.value = LoginState.Error(e.message ?: "Error desconocido")
                }
            )
        }
    }

    fun logout() {
        sessionManager.clearSession()
        _loginState.value = LoginState.Idle
    }
}
