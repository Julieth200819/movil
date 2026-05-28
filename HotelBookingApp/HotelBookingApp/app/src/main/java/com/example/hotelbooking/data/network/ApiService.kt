package com.example.hotelbooking.data.network

import com.example.hotelbooking.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body request: UserRequest): Response<Unit>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // Hotels
    @GET("/api/hotels")
    suspend fun getHotels(): Response<List<Hotel>>

    @GET("/api/hotels/{id}")
    suspend fun getHotel(@Path("id") id: Int): Response<Hotel>

    // Reservations
    @POST("/api/reservas")
    suspend fun createReserva(@Body request: ReservaRequest): Response<Reserva>

    @GET("/api/reservas/usuario/{userId}")
    suspend fun getReservasByUser(@Path("userId") userId: Int): Response<List<Reserva>>

    @DELETE("/api/reservas/{id}")
    suspend fun cancelarReserva(@Path("id") id: Int): Response<Unit>
}
