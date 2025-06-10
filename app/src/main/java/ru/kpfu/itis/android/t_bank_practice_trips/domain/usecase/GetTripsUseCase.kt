package ru.kpfu.itis.android.t_bank_practice_trips.domain.usecase

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.TripRepository
import javax.inject.Inject

class GetTripsUseCase @Inject constructor(
    private val repository: TripRepository
) {
    suspend operator fun invoke(token: String, status: TripStatus): List<Trip> =
        repository.getTrips(
            status = status,
        )
}