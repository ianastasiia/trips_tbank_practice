package ru.kpfu.itis.android.t_bank_practice_trips.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: String,
    @SerializedName("login") val login: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String?,
    @SerializedName("status") val status: String
)