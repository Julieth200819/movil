package com.example.hotelbooking.ui.hotel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelbooking.databinding.ActivityHotelDetailBinding
import com.example.hotelbooking.ui.booking.BookingActivity
import java.text.NumberFormat
import java.util.Locale

class HotelDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hotelId = intent.getIntExtra("hotel_id", 0)
        val nombre = intent.getStringExtra("hotel_nombre") ?: ""
        val ciudad = intent.getStringExtra("hotel_ciudad") ?: ""
        val descripcion = intent.getStringExtra("hotel_descripcion") ?: ""
        val precio = intent.getDoubleExtra("hotel_precio", 0.0)
        val estrellas = intent.getIntExtra("hotel_estrellas", 3)
        val amenidades = intent.getStringArrayListExtra("hotel_amenidades") ?: arrayListOf()

        binding.tvHotelNombre.text = nombre
        binding.tvHotelCiudad.text = "\uD83D\uDCCD $ciudad"
        binding.tvHotelDescripcion.text = descripcion
        binding.ratingBar.rating = estrellas.toFloat()

        val formatter = NumberFormat.getNumberInstance(Locale("es", "CO"))
        binding.tvPrecio.text = "$${formatter.format(precio.toLong())} / noche"

        // Show amenidades as chips, cleaning malformed data if necessary
        val cleanAmenidades = amenidades.map { 
            it.replace("[", "").replace("]", "").replace("\"", "").trim() 
        }.filter { it.isNotEmpty() }
        
        val amenidadesText = if (cleanAmenidades.isEmpty()) "Servicios no disponibles" 
                             else cleanAmenidades.joinToString("  ·  ") { "✓ $it" }
        binding.tvAmenidades.text = amenidadesText

        // Emoji banner based on city
        val banner = when (ciudad.lowercase()) {
            "cartagena" -> "\uD83C\uDFD6\uFE0F Cartagena de Indias"
            "bogotá", "bogota" -> "\uD83C\uDFD9\uFE0F Bogotá D.C."
            "medellín", "medellin" -> "\uD83C\uDF3C Medellín"
            "santa marta" -> "\uD83C\uDF0A Santa Marta"
            "salento" -> "\uD83C\uDF3F Salento"
            else -> "\uD83C\uDDE8\uD83C\uDDF4 $ciudad"
        }
        binding.tvCiudadBanner.text = banner

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnReservar.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("hotel_id", hotelId)
            intent.putExtra("hotel_nombre", nombre)
            intent.putExtra("hotel_precio", precio)
            startActivity(intent)
        }
    }
}
