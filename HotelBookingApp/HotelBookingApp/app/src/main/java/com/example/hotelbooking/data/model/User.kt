package com.example.hotelbooking.data.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val cedula: String,
    val nombre: String,
    val apellido: String,
    val celular: String,
    val correo: String
)

data class UserRequest(
    val cedula: String,
    val nombre: String,
    val apellido: String,
    val celular: String,
    val correo: String,
    @SerializedName("contrasena")
    val contrasena: String
)


data class LoginRequest(
    val correo: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: User
)
