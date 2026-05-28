package com.example.hotelbooking.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelbooking.data.network.RetrofitClient
import com.example.hotelbooking.data.repository.AuthRepository
import com.example.hotelbooking.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(AuthRepository(RetrofitClient.apiService))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                cedula   = binding.etCedula.text.toString().trim(),
                nombre   = binding.etNombre.text.toString().trim(),
                apellido = binding.etApellido.text.toString().trim(),
                celular  = binding.etCelular.text.toString().trim(),
                correo   = binding.etCorreo.text.toString().trim(),
                password = binding.etPassword.text.toString()
            )
        }

        binding.tvLogin.setOnClickListener { finish() }

        viewModel.registerState.observe(this) { state ->
            when (state) {
                is RegisterState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRegister.isEnabled = false
                }
                is RegisterState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "¡Registro exitoso! Inicia sesión.", Toast.LENGTH_LONG).show()
                    finish()
                }
                is RegisterState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                is RegisterState.ValidationError -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                }
            }
        }
    }
}
