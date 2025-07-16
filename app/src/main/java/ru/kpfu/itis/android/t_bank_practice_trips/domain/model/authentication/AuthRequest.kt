package ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication

data class AuthRequest(
    val phone: String,
    val password: String,
)