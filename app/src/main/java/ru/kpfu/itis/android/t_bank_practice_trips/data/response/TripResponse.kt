package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName


data class TripResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("adminId") val adminId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String?,
    @SerializedName("status") val status: String,
    @SerializedName("budget") val budget: Double?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("participantIds") val participantIds: List<Long>,
)