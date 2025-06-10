package ru.kpfu.itis.android.t_bank_practice_trips.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RefreshTokenRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.RefreshTokenResponse
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.UserResponse

interface AuthApiService {

    @GET("users/search/findByPhone")
    suspend fun login(
        @Query("phone") phone: String
    ): UserResponse

    @POST("users")
    suspend fun register(
        @Body request: RegisterRequest
    ): UserResponse

    @POST("refreshTokens")
    suspend fun createRefreshToken(
        @Body request: RefreshTokenRequest
    ): RefreshTokenResponse
}