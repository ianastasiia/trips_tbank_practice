package ru.kpfu.itis.android.t_bank_practice_trips.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.TripsListResponse

interface TripApiService {
    @GET("trips")
    suspend fun getTrips(
        @Header("Authorization") token: String
    ): TripsListResponse
}