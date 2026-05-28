package com.example.hotelbooking.ui.hotel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.data.model.Hotel
import java.text.NumberFormat
import java.util.Locale

class HotelAdapter(
    private val onClick: (Hotel) -> Unit
) : ListAdapter<Hotel, HotelAdapter.HotelViewHolder>(DiffCallback()) {

    private var originalList: List<Hotel> = emptyList()

    override fun submitList(list: List<Hotel>?) {
        originalList = list ?: emptyList()
        super.submitList(list)
    }

    fun filter(query: String) {
        if (query.isBlank()) {
            super.submitList(originalList)
        } else {
            val filtered = originalList.filter { hotel ->
                hotel.nombre.contains(query, ignoreCase = true) ||
                hotel.ciudad.contains(query, ignoreCase = true) ||
                hotel.descripcion.contains(query, ignoreCase = true)
            }
            super.submitList(filtered)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvHotelNombre)
        private val tvCiudad: TextView = itemView.findViewById(R.id.tvHotelCiudad)
        private val tvPrecio: TextView = itemView.findViewById(R.id.tvHotelPrecio)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvHotelDescripcion)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val tvAmenidades: TextView = itemView.findViewById(R.id.tvAmenidades)
        private val ivHotelIcon: TextView = itemView.findViewById(R.id.ivHotelIcon)

        fun bind(hotel: Hotel, onClick: (Hotel) -> Unit) {
            tvNombre.text = hotel.nombre
            tvCiudad.text = "\uD83D\uDCCD ${hotel.ciudad}"

            val formatter = NumberFormat.getNumberInstance(Locale("es", "CO"))
            tvPrecio.text = "$${formatter.format(hotel.precioPorNoche.toLong())}/noche"

            tvDescripcion.text = hotel.descripcion
            ratingBar.rating = hotel.estrellas.toFloat()

            val amenidades = hotel.amenidades?.take(3)?.joinToString(" · ") ?: ""
            tvAmenidades.text = amenidades

            // Hotel icon based on stars
            val iconRes = when {
                hotel.estrellas >= 5 -> "\uD83C\uDFC6"
                hotel.estrellas >= 4 -> "\uD83C\uDFE8"
                else -> "\uD83C\uDFD9\uFE0F"
            }
            ivHotelIcon.text = iconRes  // We'll use TextView as emoji display

            itemView.setOnClickListener { onClick(hotel) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Hotel>() {
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel) = oldItem == newItem
    }
}