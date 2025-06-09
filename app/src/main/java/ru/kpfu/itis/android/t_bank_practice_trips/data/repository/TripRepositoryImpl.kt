package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import ru.kpfu.itis.android.t_bank_practice_trips.data.api.TripApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.TripMapper
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.TripRepository
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(
    private val api: TripApiService,
    private val mapper: TripMapper
) : TripRepository {

    override suspend fun getTrips(token: String, status: TripStatus): List<Trip> =
        mapper.map(
            api.getTripsByStatus(
                status = status.name
            )
        )
}