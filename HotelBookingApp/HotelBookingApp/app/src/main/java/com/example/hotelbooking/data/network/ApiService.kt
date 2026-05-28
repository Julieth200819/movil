package com.example.hotelbooking.data.network

import com.example.hotelbooking.data.model.LoginRequest
import com.example.hotelbooking.data.model.User
import com.example.hotelbooking.data.model.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body request: UserRequest): Response<Unit>

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<User>
}
