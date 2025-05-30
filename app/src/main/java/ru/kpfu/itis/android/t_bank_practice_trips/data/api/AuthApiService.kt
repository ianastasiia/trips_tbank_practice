package ru.kpfu.itis.android.t_bank_practice_trips.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.LoginRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.AuthResponse

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}