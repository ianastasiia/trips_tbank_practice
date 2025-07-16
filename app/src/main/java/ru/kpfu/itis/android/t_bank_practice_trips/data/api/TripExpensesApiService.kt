package ru.kpfu.itis.android.t_bank_practice_trips.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.ExpenseRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.ExpenseResponse
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.UserResponse

interface TripExpensesApiService {
    @GET("/trips/{id}/participants")
    suspend fun getTripParticipants(
        @Path("id") tripId: Long
    ): List<UserResponse>

    @POST("/trips/{tripId}/expenses")
    suspend fun addExpense(
        @Path("tripId") tripId: Long,
        @Body request: ExpenseRequest,
    ): ExpenseResponse

}
