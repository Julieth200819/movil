package com.example.hotelbooking.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.hotelbooking.data.model.User

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("hotelbooking_prefs", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit()
            .putInt("user_id",       user.id)
            .putString("user_nombre",   user.nombre)
            .putString("user_apellido", user.apellido)
            .putString("user_correo",   user.correo)
            .putString("user_cedula",   user.cedula)
            .putString("user_celular",  user.celular)
            .putBoolean("is_logged_in", true)
            .apply()
    }

    fun getUser(): User? {
        if (!isLoggedIn()) return null
        return User(
            id       = prefs.getInt("user_id", 0),
            nombre   = prefs.getString("user_nombre",   "") ?: "",
            apellido = prefs.getString("user_apellido", "") ?: "",
            correo   = prefs.getString("user_correo",   "") ?: "",
            cedula   = prefs.getString("user_cedula",   "") ?: "",
            celular  = prefs.getString("user_celular",  "") ?: ""
        )
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
