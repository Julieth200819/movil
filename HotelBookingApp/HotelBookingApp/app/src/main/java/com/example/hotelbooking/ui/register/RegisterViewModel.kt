package com.example.hotelbooking.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelbooking.data.model.UserRequest
import com.example.hotelbooking.data.repository.AuthRepository
import com.example.hotelbooking.utils.Validators
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>(RegisterState.Idle)
    val registerState: LiveData<RegisterState> = _registerState

    fun register(
        cedula: String, nombre: String, apellido: String,
        celular: String, correo: String, password: String
    ) {
        // Validaciones en orden, una por una para mensajes claros
        when {
            !Validators.isNotBlank(cedula) ->
                return _registerState.setValue(RegisterState.ValidationError("La cédula es obligatoria"))
            !Validators.isValidCedula(cedula) ->
                return _registerState.setValue(RegisterState.ValidationError("La cédula solo debe tener números (6-12 dígitos)"))
            !Validators.isNotBlank(nombre) ->
                return _registerState.setValue(RegisterState.ValidationError("El nombre es obligatorio"))
            !Validators.isNotBlank(apellido) ->
                return _registerState.setValue(RegisterState.ValidationError("El apellido es obligatorio"))
            !Validators.isNotBlank(celular) ->
                return _registerState.setValue(RegisterState.ValidationError("El celular es obligatorio"))
            !Validators.isValidPhone(celular) ->
                return _registerState.setValue(RegisterState.ValidationError("El celular debe tener 10 dígitos numéricos"))
            !Validators.isNotBlank(correo) ->
                return _registerState.setValue(RegisterState.ValidationError("El correo es obligatorio"))
            !Validators.isValidEmail(correo) ->
                return _registerState.setValue(RegisterState.ValidationError("Ingresa un correo válido"))
            !Validators.isNotBlank(password) ->
                return _registerState.setValue(RegisterState.ValidationError("La contraseña es obligatoria"))
            password.length < 6 ->
                return _registerState.setValue(RegisterState.ValidationError("La contraseña debe tener mínimo 6 caracteres"))
        }

        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val result = repository.register(
                UserRequest(cedula, nombre, apellido, celular, correo, password)
            )
            result.fold(
                onSuccess = { _registerState.value = RegisterState.Success },
                onFailure = { e -> _registerState.value = RegisterState.Error(e.message ?: "Error desconocido") }
            )
        }
    }
}
