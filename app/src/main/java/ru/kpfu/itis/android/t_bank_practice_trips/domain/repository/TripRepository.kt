package ru.kpfu.itis.android.t_bank_practice_trips.domain.repository

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.Expense

interface TripRepository {
    suspend fun getTrips(
        status: TripStatus,
    ): List<Trip>

    suspend fun getTripById(tripId: Long): Trip

    suspend fun getExpensesByTrip(tripId: Long): List<Expense>
}