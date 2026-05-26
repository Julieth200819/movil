package com.hotelbooking.app.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.hotelbooking.app.databinding.ActivityLoginBinding
import com.hotelbooking.app.models.ErrorResponse
import com.hotelbooking.app.models.LoginRequest
import com.hotelbooking.app.network.RetrofitClient
import com.hotelbooking.app.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { intentarLogin() }
        binding.tvIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun intentarLogin() {
        val correo     = binding.etCorreo.text.toString().trim()
        val contrasena = binding.etContrasena.text.toString()

        // ── Validaciones en cliente ──────────────────────
        if (correo.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.etCorreo.error = "Correo no válido"
            return
        }

        setLoading(true)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.authApi.login(
                    LoginRequest(correo = correo, contrasena = contrasena)
                )

                if (response.isSuccessful) {
                    val body = response.body()!!
                    SessionManager.saveSession(this@LoginActivity, body)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    val errorJson = response.errorBody()?.string()
                    val mensaje = try {
                        Gson().fromJson(errorJson, ErrorResponse::class.java).mensaje
                    } catch (e: Exception) {
                        "Correo o contraseña incorrectos"
                    }
                    Toast.makeText(this@LoginActivity, mensaje, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
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
        binding.btnLogin.isEnabled = !loading
    }
}
