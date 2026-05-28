package com.example.hotelbooking.data.repository

import com.example.hotelbooking.data.model.LoginRequest
import com.example.hotelbooking.data.model.User
import com.example.hotelbooking.data.model.UserRequest
import com.example.hotelbooking.data.network.ApiService

class AuthRepository(private val api: ApiService) {

    suspend fun register(request: UserRequest): Result<Unit> {
        return try {
            val response = api.register(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorMsg = when (response.code()) {
                    409  -> "El correo o cédula ya están registrados"
                    400 -> {
                        val detail = response.errorBody()?.string() ?: "Datos inválidos"
                        "Error 400: $detail"
                    }
                    else -> "Error del servidor (${response.code()})"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            e.printStackTrace() // Esto permite ver el error real en Logcat
            Result.failure(Exception("Error de red: ${e.message}"))
        }
    }

    suspend fun login(correo: String, password: String): Result<User> {
        return try {
            val response = api.login(LoginRequest(correo, password))
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Respuesta del servidor vacía"))
                }
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }
}
