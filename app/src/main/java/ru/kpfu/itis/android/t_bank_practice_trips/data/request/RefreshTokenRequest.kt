package ru.kpfu.itis.android.t_bank_practice_trips.data.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("userId") val userId: Long,
)
