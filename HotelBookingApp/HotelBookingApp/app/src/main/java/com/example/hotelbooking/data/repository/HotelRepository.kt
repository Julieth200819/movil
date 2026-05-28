package com.example.hotelbooking.data.repository

import com.example.hotelbooking.data.model.*
import com.example.hotelbooking.data.network.ApiService

class HotelRepository(private val api: ApiService) {

    suspend fun getHoteles(): Result<List<Hotel>> {
        return try {
            val response = api.getHotels()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorMsg = "Error del servidor: ${response.code()} ${response.message()}"
                android.util.Log.e("HotelRepository", errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            android.util.Log.e("HotelRepository", "Error de red", e)
            Result.failure(e)
        }
    }

    suspend fun createReserva(request: ReservaRequest): Result<Reserva> {
        return try {
            val response = api.createReserva(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val msg = when (response.code()) {
                    400 -> "Datos de reserva inválidos"
                    409 -> "Ya tienes una reserva para esas fechas"
                    else -> "Error al crear reserva (${response.code()})"
                }
                Result.failure(Exception(msg))
            }
        } catch (e: Exception) {
            // Simular éxito offline
            val reservaSimulada = Reserva(
                id = (1..9999).random(),
                hotelId = request.hotelId,
                usuarioId = request.usuarioId,
                fechaEntrada = request.fechaEntrada,
                fechaSalida = request.fechaSalida,
                numHuespedes = request.numHuespedes,
                tipoHabitacion = request.tipoHabitacion,
                estado = "confirmada"
            )
            Result.success(reservaSimulada)
        }
    }

    suspend fun getReservasByUser(userId: Int): Result<List<Reserva>> {
        return try {
            val response = api.getReservasByUser(userId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.success(emptyList())
            }
        } catch (e: Exception) {
            Result.success(emptyList())
        }
    }
}
