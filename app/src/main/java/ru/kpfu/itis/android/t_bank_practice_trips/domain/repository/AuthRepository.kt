package ru.kpfu.itis.android.t_bank_practice_trips.domain.repository

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest

interface AuthRepository {
    suspend fun login(request: AuthRequest): AuthResponse

    suspend fun register(request: RegisterRequest): AuthResponse
}