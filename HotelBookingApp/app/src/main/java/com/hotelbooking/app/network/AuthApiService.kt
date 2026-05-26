package com.hotelbooking.app.network

import com.hotelbooking.app.models.LoginRequest
import com.hotelbooking.app.models.LoginResponse
import com.hotelbooking.app.models.RegisterRequest
import com.hotelbooking.app.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
