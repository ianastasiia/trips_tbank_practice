package ru.kpfu.itis.android.t_bank_practice_trips.domain.repository

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip

interface TripRepository {
    suspend fun getTrips(): List<Trip>
}