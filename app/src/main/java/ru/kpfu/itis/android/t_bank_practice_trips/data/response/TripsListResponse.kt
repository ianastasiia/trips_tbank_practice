package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName

data class TripsListResponse(
    @SerializedName("items") val items: List<TripResponse>
)