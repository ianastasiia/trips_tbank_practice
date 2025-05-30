package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName


data class TripResponse(
    @SerializedName("id") val id: String,
    @SerializedName("admin_id") val adminId: String,
    @SerializedName("title") val title: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
)