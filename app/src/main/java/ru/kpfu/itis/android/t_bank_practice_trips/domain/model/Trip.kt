package ru.kpfu.itis.android.t_bank_practice_trips.domain.model

enum class TripStatus { ACTIVE, PLANNING, COMPLETED, CANCELLED }

data class Trip(
    val id: String,
    val adminId: String,
    val title: String,
    val startDate: String,
    val endDate: String?,
    val status: TripStatus,
    val createdAt: String,
) {
    val dateRange: String
        get() = "${startDate.toFormattedDate()} - ${endDate?.toFormattedDate() ?: "..."}"

    private fun String.toFormattedDate(): String {
        return this.split("-").reversed().joinToString(".")
    }
}