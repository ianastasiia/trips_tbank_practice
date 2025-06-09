package ru.kpfu.itis.android.t_bank_practice_trips.data.mapper

import ru.kpfu.itis.android.t_bank_practice_trips.data.response.TripResponse
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.TripsListResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import javax.inject.Inject

class TripMapper @Inject constructor() {
    fun map(response: TripsListResponse): List<Trip> {
        return response.items.map { item -> map(item) }
    }

    fun map(response: TripResponse): Trip {
        return Trip(
            id = response.id,
            adminId = response.adminId,
            title = response.title,
            startDate = response.startDate,
            endDate = response.endDate,
            status = mapStatus(response.status),
            budget = response.budget,
            createdAt = response.createdAt,
            participantIds = response.participantIds,
        )
    }

    private fun mapStatus(status: String): TripStatus {
        return when (status) {
            "ACTIVE" -> TripStatus.ACTIVE
            "COMPLETED" -> TripStatus.COMPLETED
            "DELETED" -> TripStatus.DELETED
            else -> TripStatus.PLANNING
        }
    }
}