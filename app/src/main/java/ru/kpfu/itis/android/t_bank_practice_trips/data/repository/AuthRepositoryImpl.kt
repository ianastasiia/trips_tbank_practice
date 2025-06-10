package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import android.util.Base64
import ru.kpfu.itis.android.t_bank_practice_trips.data.api.AuthApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.auth.AuthManager
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.AuthMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RefreshTokenRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.AuthRepository
import java.security.SecureRandom
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val mapper: AuthMapper,
    private val authManager: AuthManager
) : AuthRepository {

    override suspend fun login(request: AuthRequest): AuthResponse {
        val userResponse = apiService.login(request.phone)
        authManager.saveToken(userResponse.id.toString())

        val refreshToken = createRefreshTokenForUser(userResponse.id)
        authManager.saveRefreshToken(refreshToken)
        return AuthResponse(
            token = userResponse.id.toString(), user = mapper.map(userResponse)
        )
    }

    override suspend fun register(request: RegisterRequest): AuthResponse {
        val registerRequest = mapper.mapToRegisterRequest(request)
        val userResponse = apiService.register(registerRequest)
        authManager.saveToken(userResponse.id.toString())

        val refreshToken = createRefreshTokenForUser(userResponse.id)
        authManager.saveRefreshToken(refreshToken)

        return AuthResponse(
            token = userResponse.id.toString(), user = mapper.map(userResponse)
        )
    }

    override suspend fun getToken(): String? {
        return authManager.getToken()
    }

    private suspend fun createRefreshTokenForUser(userId: Long): String {
        val request = RefreshTokenRequest(
            user = "/users/$userId", token = generateSecureToken()
        )
        return apiService.createRefreshToken(request).token
    }

    private fun generateSecureToken(): String {
        val bytes = ByteArray(32)
        SecureRandom().nextBytes(bytes)
        return Base64.encodeToString(bytes, Base64.URL_SAFE or Base64.NO_WRAP)
    }
}