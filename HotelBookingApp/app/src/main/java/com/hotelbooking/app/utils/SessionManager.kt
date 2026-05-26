package com.hotelbooking.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.hotelbooking.app.models.LoginResponse

object SessionManager {

    private const val PREF_NAME = "HotelBookingSession"
    private const val KEY_ID       = "user_id"
    private const val KEY_NOMBRE   = "user_nombre"
    private const val KEY_APELLIDO = "user_apellido"
    private const val KEY_CORREO   = "user_correo"
    private const val KEY_CEDULA   = "user_cedula"
    private const val KEY_CELULAR  = "user_celular"
    private const val KEY_LOGGED   = "is_logged_in"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveSession(context: Context, user: LoginResponse) {
        prefs(context).edit().apply {
            putInt(KEY_ID, user.id)
            putString(KEY_NOMBRE, user.nombre)
            putString(KEY_APELLIDO, user.apellido)
            putString(KEY_CORREO, user.correo)
            putString(KEY_CEDULA, user.cedula)
            putString(KEY_CELULAR, user.celular)
            putBoolean(KEY_LOGGED, true)
            apply()
        }
    }

    fun isLoggedIn(context: Context): Boolean =
        prefs(context).getBoolean(KEY_LOGGED, false)

    fun getNombre(context: Context): String =
        prefs(context).getString(KEY_NOMBRE, "") ?: ""

    fun getApellido(context: Context): String =
        prefs(context).getString(KEY_APELLIDO, "") ?: ""

    fun getCorreo(context: Context): String =
        prefs(context).getString(KEY_CORREO, "") ?: ""

    fun getCedula(context: Context): String =
        prefs(context).getString(KEY_CEDULA, "") ?: ""

    fun getCelular(context: Context): String =
        prefs(context).getString(KEY_CELULAR, "") ?: ""

    fun clearSession(context: Context) {
        prefs(context).edit().clear().apply()
    }
}
