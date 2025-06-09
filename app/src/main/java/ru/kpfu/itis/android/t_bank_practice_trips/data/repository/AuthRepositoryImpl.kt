package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import ru.kpfu.itis.android.t_bank_practice_trips.data.api.AuthApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.auth.AuthManager
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.AuthMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RefreshTokenRequest
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
        val userResponse = apiService.login(request.phone)
        authManager.saveToken(userResponse.id.toString())
        return AuthResponse(
            token = userResponse.id.toString(),
            user = mapper.map(userResponse)
        )
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        val registerRequest = mapper.mapToRegisterRequest(request)
        val userResponse = apiService.register(registerRequest)
        authManager.saveToken(userResponse.id.toString())

        val refreshToken = apiService.createRefreshToken(
            RefreshTokenRequest(userId = userResponse.id)
        )
        authManager.saveRefreshToken(refreshToken.token)

        return AuthResponse(
            token = userResponse.id.toString(),
            user = mapper.map(userResponse)
        )
    }

    override suspend fun getToken(): String? {
        return authManager.getToken()
    }
}