package ru.kpfu.itis.android.t_bank_practice_trips.data.mapper

import ru.kpfu.itis.android.t_bank_practice_trips.data.request.LoginRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.AuthResponse as NetworkAuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.UserResponse as NetworkUserResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthResponse as DomainAuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.User as DomainUser
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthRequest as DomainAuthRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest as DomainRegisterRequest

class AuthMapper {

    fun mapToLoginRequest(request: DomainAuthRequest): LoginRequest {
        return LoginRequest(
            login = request.phone,
            password = request.password,
        )
    }

    fun mapToRegisterRequest(request: DomainRegisterRequest): RegisterRequest {
        return RegisterRequest(
            login = request.phone,
            phone = request.phone,
            password = request.password,
            name = request.name,
            surname = request.surname
        )
    }

    fun map(response: NetworkAuthResponse): DomainAuthResponse {
        return DomainAuthResponse(
            token = response.token, user = mapUser(response.user)
        )
    }

    private fun mapUser(userResponse: NetworkUserResponse): DomainUser {
        return DomainUser(
            id = userResponse.id,
            login = userResponse.login,
            phone = userResponse.phone,
            name = userResponse.name,
            surname = userResponse.surname,
            status = userResponse.status
        )
    }
}