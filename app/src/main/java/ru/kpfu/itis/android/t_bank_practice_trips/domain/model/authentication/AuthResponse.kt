package ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication

data class AuthResponse(
    val token: String,
    val user: User,
)
