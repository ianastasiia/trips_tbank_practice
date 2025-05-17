package ru.kpfu.itis.android.t_bank_practice_trips.domain.model

enum class TripStatus { ACTIVE, COMPLETED }

data class Trip(
    val title: String,
    val dateRange: String,
    val status: TripStatus
)