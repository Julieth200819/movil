package com.hotelbooking.app.models

import com.google.gson.annotations.SerializedName

// ──────────────────────────────────────────────
// REQUEST bodies
// ──────────────────────────────────────────────

data class RegisterRequest(
    @SerializedName("cedula")     val cedula: String,
    @SerializedName("nombre")     val nombre: String,
    @SerializedName("apellido")   val apellido: String,
    @SerializedName("celular")    val celular: String,
    @SerializedName("correo")     val correo: String,
    @SerializedName("contrasena") val contrasena: String
)

data class LoginRequest(
    @SerializedName("correo")     val correo: String,
    @SerializedName("contrasena") val contrasena: String
)

// ──────────────────────────────────────────────
// RESPONSE bodies
// ──────────────────────────────────────────────

data class RegisterResponse(
    @SerializedName("mensaje") val mensaje: String
)

data class LoginResponse(
    @SerializedName("id")       val id: Int,
    @SerializedName("nombre")   val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("correo")   val correo: String,
    @SerializedName("cedula")   val cedula: String,
    @SerializedName("celular")  val celular: String,
    @SerializedName("mensaje")  val mensaje: String
)

data class ErrorResponse(
    @SerializedName("mensaje") val mensaje: String
)
