package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName


data class TripResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("admin_id") val adminId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("status") val status: String,
    @SerializedName("budget") val budget: Double?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("participant_id") val participantIds: List<Long>,
)