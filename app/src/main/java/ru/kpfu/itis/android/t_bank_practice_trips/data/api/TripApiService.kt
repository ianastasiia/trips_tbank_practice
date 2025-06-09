package ru.kpfu.itis.android.t_bank_practice_trips.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.TripResponse

interface TripApiService {
    @GET("api/v1/trips")
    suspend fun getTripsByStatus(
        @Header("Authorization") token: String,
        @Query("status") status: String
    ): List<TripResponse>
}