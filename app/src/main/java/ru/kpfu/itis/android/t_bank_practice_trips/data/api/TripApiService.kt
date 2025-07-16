package ru.kpfu.itis.android.t_bank_practice_trips.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.ExpenseResponse
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.TripResponse

interface TripApiService {
    @GET("api/v1/trips")
    suspend fun getTripsByStatus(
        @Query("status") status: String
    ): List<TripResponse>

    @GET("api/v1/trips/{tripId}")
    suspend fun getTripById(
        @Path("tripId") tripId: Long
    ): TripResponse

    @GET("api/v1/trips/{tripId}/expenses")
    suspend fun getExpensesByTrip(
        @Path("tripId") tripId: Long
    ): List<ExpenseResponse>
}