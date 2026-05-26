package com.hotelbooking.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hotelbooking.app.databinding.ActivityHomeBinding
import com.hotelbooking.app.utils.SessionManager

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre   = SessionManager.getNombre(this)
        val apellido = SessionManager.getApellido(this)
        val correo   = SessionManager.getCorreo(this)
        val cedula   = SessionManager.getCedula(this)
        val celular  = SessionManager.getCelular(this)

        binding.tvBienvenida.text = "¡Bienvenido, $nombre!"
        binding.tvNombreCompleto.text = "$nombre $apellido"
        binding.tvCorreo.text  = correo
        binding.tvCedula.text  = cedula
        binding.tvCelular.text = celular

        binding.btnLogout.setOnClickListener { mostrarConfirmacionLogout() }
    }

    private fun mostrarConfirmacionLogout() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                SessionManager.clearSession(this)
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
