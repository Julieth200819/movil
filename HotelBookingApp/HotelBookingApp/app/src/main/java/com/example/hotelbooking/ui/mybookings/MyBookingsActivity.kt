package com.example.hotelbooking.ui.mybookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.data.model.Reserva
import com.example.hotelbooking.data.network.RetrofitClient
import com.example.hotelbooking.data.repository.HotelRepository
import com.example.hotelbooking.databinding.ActivityMyBookingsBinding
import com.example.hotelbooking.utils.SessionManager
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class MyBookingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyBookingsBinding
    private lateinit var session: SessionManager
    private lateinit var repository: HotelRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBookingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        repository = HotelRepository(RetrofitClient.apiService)

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        loadReservas()
    }

    private fun loadReservas() {
        val userId = session.getUser()?.id ?: return
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            val result = repository.getReservasByUser(userId)
            binding.progressBar.visibility = View.GONE

            result.onSuccess { reservas ->
                if (reservas.isEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                    binding.rvReservas.visibility = View.GONE
                } else {
                    binding.tvEmpty.visibility = View.GONE
                    binding.rvReservas.visibility = View.VISIBLE
                    binding.rvReservas.layoutManager = LinearLayoutManager(this@MyBookingsActivity)
                    binding.rvReservas.adapter = ReservasAdapter(reservas)
                }
            }
        }
    }
}

class ReservasAdapter(private val items: List<Reserva>) :
    RecyclerView.Adapter<ReservasAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])
    override fun getItemCount() = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(r: Reserva) {
            itemView.findViewById<TextView>(R.id.tvReservaId).text = "Reserva #${r.id}"
            itemView.findViewById<TextView>(R.id.tvHotelNombre).text = r.hotel?.nombre ?: "Hotel ID: ${r.hotelId}"
            itemView.findViewById<TextView>(R.id.tvFechas).text = "${r.fechaEntrada} → ${r.fechaSalida}"
            itemView.findViewById<TextView>(R.id.tvTipoHab).text = r.tipoHabitacion
            itemView.findViewById<TextView>(R.id.tvHuespedes).text = "${r.numHuespedes} huésped(es)"
            val estado = r.estado ?: "confirmada"
            val tvEstado = itemView.findViewById<TextView>(R.id.tvEstado)
            tvEstado.text = estado.uppercase()
            val color = when (estado.lowercase()) {
                "confirmada" -> android.R.color.holo_green_dark
                "cancelada" -> android.R.color.holo_red_dark
                else -> android.R.color.holo_blue_dark
            }
            tvEstado.setTextColor(itemView.context.getColor(color))
        }
    }
}
