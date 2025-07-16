package ru.kpfu.itis.android.t_bank_practice_trips.domain.model

enum class TripStatus { ACTIVE, PLANNING, COMPLETED, DELETED }

data class Trip(
    val id: Long,
    val adminId: Long,
    val title: String,
    val startDate: String,
    val endDate: String?,
    val status: TripStatus,
    val budget: Double? = null,
    val createdAt: String? = "",
    val participantIds: List<Long>? = null,
) {
    val dateRange: String
        get() = "${startDate.toFormattedDate()} - ${endDate?.toFormattedDate() ?: "..."}"

    private fun String.toFormattedDate(): String {
        return this.split("-").reversed().joinToString(".")
    }
}