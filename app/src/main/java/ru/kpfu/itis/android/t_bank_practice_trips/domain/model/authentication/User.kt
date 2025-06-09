package ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication

data class User(
    val id: String,
    val phone: String,
    val name: String,
    val surname: String? = null,
    val status: String,
)
