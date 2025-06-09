package ru.kpfu.itis.android.t_bank_practice_trips.domain.repository

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus

interface TripRepository {
    suspend fun getTrips(
        token: String,
        status: TripStatus,
    ): List<Trip>
}