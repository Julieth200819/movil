package com.example.hotelbooking.data.model

import com.google.gson.annotations.SerializedName

data class Hotel(
    val id: Int,
    @SerializedName("nombre")
    val nombre: String,
    @SerializedName("ubicacion")
    val ciudad: String,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("precio")
    val precioPorNoche: Double,
    @SerializedName("estrellas")
    val estrellas: Int = 0,
    @SerializedName("imagenUrl")
    val imagenUrl: String? = null,
    @SerializedName("servicios")
    val amenidades: List<String>? = null
)

data class Reserva(
    val id: Int? = null,
    @SerializedName("hotel_id")
    val hotelId: Int,
    @SerializedName("usuario_id")
    val usuarioId: Int,
    @SerializedName("fecha_entrada")
    val fechaEntrada: String,
    @SerializedName("fecha_salida")
    val fechaSalida: String,
    @SerializedName("num_huespedes")
    val numHuespedes: Int,
    @SerializedName("tipo_habitacion")
    val tipoHabitacion: String,
    @SerializedName("precio_total")
    val precioTotal: Double? = null,
    val estado: String? = null,
    val hotel: Hotel? = null
)

data class ReservaRequest(
    @SerializedName("hotel_id")
    val hotelId: Int,
    @SerializedName("usuario_id")
    val usuarioId: Int,
    @SerializedName("fecha_entrada")
    val fechaEntrada: String,
    @SerializedName("fecha_salida")
    val fechaSalida: String,
    @SerializedName("num_huespedes")
    val numHuespedes: Int,
    @SerializedName("tipo_habitacion")
    val tipoHabitacion: String
)
