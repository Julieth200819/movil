package com.example.hotelbooking.ui.booking

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hotelbooking.data.model.ReservaRequest
import com.example.hotelbooking.data.network.RetrofitClient
import com.example.hotelbooking.data.repository.HotelRepository
import com.example.hotelbooking.databinding.ActivityBookingBinding
import com.example.hotelbooking.utils.SessionManager
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private lateinit var session: SessionManager
    private lateinit var repository: HotelRepository

    private var fechaEntrada: Calendar? = null
    private var fechaSalida: Calendar? = null
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val sdfDisplay = SimpleDateFormat("dd 'de' MMMM, yyyy", Locale("es", "CO"))

    private var hotelId = 0
    private var precioPorNoche = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hotelId = intent.getIntExtra("hotel_id", 0)
        val hotelNombre = intent.getStringExtra("hotel_nombre") ?: ""
        precioPorNoche = intent.getDoubleExtra("hotel_precio", 0.0)

        session = SessionManager(this)
        repository = HotelRepository(RetrofitClient.apiService)

        val formatter = NumberFormat.getNumberInstance(Locale("es", "CO"))
        binding.tvHotelNombre.text = hotelNombre
        binding.tvPrecioNoche.text = "$${formatter.format(precioPorNoche.toLong())} / noche"

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.btnFechaEntrada.setOnClickListener { showDatePicker(isEntrada = true) }
        binding.btnFechaSalida.setOnClickListener { showDatePicker(isEntrada = false) }

        binding.btnConfirmarReserva.setOnClickListener { confirmarReserva() }

        // Setup guests spinner
        setupGuestsSelector()
    }

    private fun setupGuestsSelector() {
        var guests = 1
        binding.tvGuests.text = "$guests huésped"
        binding.btnGuestsPlus.setOnClickListener {
            if (guests < 10) {
                guests++
                binding.tvGuests.text = if (guests == 1) "1 huésped" else "$guests huéspedes"
                updatePrecioTotal()
            }
        }
        binding.btnGuestsMinus.setOnClickListener {
            if (guests > 1) {
                guests--
                binding.tvGuests.text = if (guests == 1) "1 huésped" else "$guests huéspedes"
                updatePrecioTotal()
            }
        }
    }

    private fun showDatePicker(isEntrada: Boolean) {
        val cal = Calendar.getInstance()
        if (isEntrada) {
            // Min date = today
        } else {
            // Min date = day after entrada
            fechaEntrada?.let { cal.time = it.time; cal.add(Calendar.DAY_OF_MONTH, 1) }
        }

        val picker = DatePickerDialog(
            this,
            { _, year, month, day ->
                val selected = Calendar.getInstance()
                selected.set(year, month, day)
                if (isEntrada) {
                    fechaEntrada = selected
                    binding.btnFechaEntrada.text = sdfDisplay.format(selected.time)
                    binding.btnFechaEntrada.setTextColor(getColor(android.R.color.black))
                } else {
                    fechaSalida = selected
                    binding.btnFechaSalida.text = sdfDisplay.format(selected.time)
                    binding.btnFechaSalida.setTextColor(getColor(android.R.color.black))
                }
                updatePrecioTotal()
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )

        // Set minimum date
        picker.datePicker.minDate = if (isEntrada) {
            System.currentTimeMillis()
        } else {
            fechaEntrada?.timeInMillis?.plus(86400000L) ?: System.currentTimeMillis() + 86400000L
        }

        picker.show()
    }

    private fun updatePrecioTotal() {
        val entrada = fechaEntrada ?: return
        val salida = fechaSalida ?: return

        val diffMillis = salida.timeInMillis - entrada.timeInMillis
        val nights = (diffMillis / 86400000L).toInt()

        if (nights > 0) {
            val total = nights * precioPorNoche
            val formatter = NumberFormat.getNumberInstance(Locale("es", "CO"))
            binding.tvNochesInfo.text = "$nights noche${if (nights > 1) "s" else ""}"
            binding.tvPrecioTotal.text = "$${formatter.format(total.toLong())}"
            binding.cardPrecioTotal.visibility = View.VISIBLE
        } else {
            binding.cardPrecioTotal.visibility = View.GONE
        }
    }

    private fun confirmarReserva() {
        val entrada = fechaEntrada
        val salida = fechaSalida

        if (entrada == null) { showError("Selecciona la fecha de entrada"); return }
        if (salida == null) { showError("Selecciona la fecha de salida"); return }

        val diffMillis = salida.timeInMillis - entrada.timeInMillis
        val nights = (diffMillis / 86400000L).toInt()
        if (nights <= 0) { showError("La fecha de salida debe ser después de la entrada"); return }

        val tipoHabitacion = when (binding.rgTipoHabitacion.checkedRadioButtonId) {
            binding.rbEstandar.id -> "Estándar"
            binding.rbSuperior.id -> "Superior"
            binding.rbSuite.id -> "Suite"
            else -> "Estándar"
        }

        val guestsText = binding.tvGuests.text.toString()
        val numGuests = guestsText.split(" ")[0].toIntOrNull() ?: 1

        val userId = session.getUser()?.id ?: 0

        val request = ReservaRequest(
            hotelId = hotelId,
            usuarioId = userId,
            fechaEntrada = sdf.format(entrada.time),
            fechaSalida = sdf.format(salida.time),
            numHuespedes = numGuests,
            tipoHabitacion = tipoHabitacion
        )

        binding.btnConfirmarReserva.isEnabled = false
        binding.progressReserva.visibility = View.VISIBLE

        lifecycleScope.launch {
            val result = repository.createReserva(request)
            binding.btnConfirmarReserva.isEnabled = true
            binding.progressReserva.visibility = View.GONE

            result.onSuccess { reserva ->
                showSuccessDialog(reserva.id ?: 0, tipoHabitacion, nights)
            }.onFailure { error ->
                showError(error.message ?: "Error al crear la reserva")
            }
        }
    }

    private fun showSuccessDialog(reservaId: Int, tipoHab: String, noches: Int) {
        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("✅ ¡Reserva Confirmada!")
            .setMessage(
                "Tu reserva ha sido creada exitosamente.\n\n" +
                "📋 ID de reserva: #$reservaId\n" +
                "🛏 Habitación: $tipoHab\n" +
                "🌙 Noches: $noches\n\n" +
                "Recibirás un correo de confirmación."
            )
            .setPositiveButton("Ver mis reservas") { _, _ ->
                finish()
            }
            .setNegativeButton("Inicio") { _, _ ->
                finishAffinity()
                startActivity(android.content.Intent(this, com.example.hotelbooking.ui.home.HomeActivity::class.java))
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun showError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
