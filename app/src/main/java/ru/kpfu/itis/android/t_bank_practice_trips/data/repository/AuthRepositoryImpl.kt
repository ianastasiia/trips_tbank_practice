package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import ru.kpfu.itis.android.t_bank_practice_trips.data.api.AuthApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.auth.AuthManager
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.AuthMapper
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val mapper: AuthMapper,
    private val authManager: AuthManager
) : AuthRepository {

    override suspend fun login(request: AuthRequest): AuthResponse {
        val response = apiService.login(mapper.mapToLoginRequest(request))
        authManager.saveToken(response.token)
        return mapper.map(response)
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        val response = apiService.register(mapper.mapToRegisterRequest(request))
        authManager.saveToken(response.token)
        return mapper.map(response)
    }

}