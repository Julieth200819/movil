package com.hotelbooking.app.activities

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.hotelbooking.app.databinding.ActivityRegisterBinding
import com.hotelbooking.app.models.ErrorResponse
import com.hotelbooking.app.models.RegisterRequest
import com.hotelbooking.app.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener { intentarRegistro() }
        binding.tvVolver.setOnClickListener { finish() }
    }

    private fun intentarRegistro() {
        val cedula     = binding.etCedula.text.toString().trim()
        val nombre     = binding.etNombre.text.toString().trim()
        val apellido   = binding.etApellido.text.toString().trim()
        val celular    = binding.etCelular.text.toString().trim()
        val correo     = binding.etCorreo.text.toString().trim()
        val contrasena = binding.etContrasena.text.toString()
        val confirmar  = binding.etConfirmarContrasena.text.toString()

        // ── Validaciones en cliente ──────────────────────
        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
            celular.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.etCorreo.error = "Correo no válido"
            return
        }
        if (contrasena.length < 6) {
            binding.etContrasena.error = "La contraseña debe tener al menos 6 caracteres"
            return
        }
        if (contrasena != confirmar) {
            binding.etConfirmarContrasena.error = "Las contraseñas no coinciden"
            return
        }

        setLoading(true)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.authApi.register(
                    RegisterRequest(
                        cedula     = cedula,
                        nombre     = nombre,
                        apellido   = apellido,
                        celular    = celular,
                        correo     = correo,
                        contrasena = contrasena
                    )
                )

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "✅ Registro exitoso. Ahora inicia sesión.",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {
                    val errorJson = response.errorBody()?.string()
                    val mensaje = try {
                        Gson().fromJson(errorJson, ErrorResponse::class.java).mensaje
                    } catch (e: Exception) {
                        "Error al registrar usuario"
                    }
                    Toast.makeText(this@RegisterActivity, mensaje, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnRegistrar.isEnabled = !loading
    }
}
