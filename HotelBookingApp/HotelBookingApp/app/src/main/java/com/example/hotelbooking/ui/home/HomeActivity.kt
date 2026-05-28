package com.example.hotelbooking.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelbooking.databinding.ActivityHomeBinding
import com.example.hotelbooking.ui.login.LoginActivity
import com.example.hotelbooking.utils.SessionManager

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val session = SessionManager(this)
        val user = session.getUser()

        // Mostrar bienvenida con nombre del usuario
        binding.tvWelcome.text = "¡Bienvenido, ${user?.nombre} ${user?.apellido}!"
        binding.tvEmail.text   = user?.correo ?: ""

        binding.btnLogout.setOnClickListener {
            session.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity() // Cierra todas las activities del stack
        }
    }
}
