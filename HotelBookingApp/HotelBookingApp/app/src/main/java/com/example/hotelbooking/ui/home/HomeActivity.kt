package com.example.hotelbooking.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelbooking.data.model.Hotel
import com.example.hotelbooking.data.network.RetrofitClient
import com.example.hotelbooking.data.repository.HotelRepository
import com.example.hotelbooking.databinding.ActivityHomeBinding
import com.example.hotelbooking.ui.hotel.HotelAdapter
import com.example.hotelbooking.ui.hotel.HotelDetailActivity
import com.example.hotelbooking.ui.login.LoginActivity
import com.example.hotelbooking.ui.mybookings.MyBookingsActivity
import com.example.hotelbooking.utils.SessionManager
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var session: SessionManager
    private lateinit var repository: HotelRepository
    private lateinit var adapter: HotelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        repository = HotelRepository(RetrofitClient.apiService)

        setupUI()
        setupRecyclerView()
        loadHoteles()
    }

    private fun setupUI() {
        val user = session.getUser()
        binding.tvWelcome.text = "Hola, ${user?.nombre} \uD83D\uDC4B"
        binding.tvSubtitle.text = "¿A dónde quieres viajar hoy?"

        binding.btnLogout.setOnClickListener {
            session.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        binding.btnMyBookings.setOnClickListener {
            startActivity(Intent(this, MyBookingsActivity::class.java))
        }

        binding.etSearch.setOnEditorActionListener { v, _, _ ->
            val query = v.text.toString()
            filterHoteles(query)
            true
        }

        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterHoteles(s.toString())
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })
    }

    private fun setupRecyclerView() {
        adapter = HotelAdapter { hotel ->
            val intent = Intent(this, HotelDetailActivity::class.java)
            intent.putExtra("hotel_id", hotel.id)
            intent.putExtra("hotel_nombre", hotel.nombre)
            intent.putExtra("hotel_ciudad", hotel.ciudad)
            intent.putExtra("hotel_descripcion", hotel.descripcion)
            intent.putExtra("hotel_precio", hotel.precioPorNoche)
            intent.putExtra("hotel_estrellas", hotel.estrellas)
            intent.putStringArrayListExtra("hotel_amenidades", ArrayList(hotel.amenidades ?: emptyList()))
            startActivity(intent)
        }
        binding.rvHoteles.layoutManager = LinearLayoutManager(this)
        binding.rvHoteles.adapter = adapter
    }

    private fun loadHoteles() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvHoteles.visibility = View.GONE

        lifecycleScope.launch {
            val result = repository.getHoteles()
            binding.progressBar.visibility = View.GONE
            binding.rvHoteles.visibility = View.VISIBLE

            result.onSuccess { hoteles ->
                adapter.submitList(hoteles)
                binding.tvHotelCount.text = "${hoteles.size} hoteles disponibles"
            }.onFailure {
                Toast.makeText(this@HomeActivity, "Error al cargar hoteles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun filterHoteles(query: String) {
        adapter.filter(query)
    }
}
