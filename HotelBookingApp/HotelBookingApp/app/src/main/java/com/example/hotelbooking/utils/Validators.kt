package com.example.hotelbooking.utils

object Validators {

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.matches(Regex("^[0-9]{10}$"))
    }

    fun isValidCedula(cedula: String): Boolean {
        return cedula.matches(Regex("^[0-9]{6,12}$"))
    }

    fun isNotBlank(value: String): Boolean = value.trim().isNotEmpty()
}
