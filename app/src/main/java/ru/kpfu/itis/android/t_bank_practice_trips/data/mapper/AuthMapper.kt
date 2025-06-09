package ru.kpfu.itis.android.t_bank_practice_trips.data.mapper

import ru.kpfu.itis.android.t_bank_practice_trips.data.request.LoginRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.AuthResponse as NetworkAuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.UserResponse as NetworkUserResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthRequest as DomainAuthRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthResponse as DomainAuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest as DomainRegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.User as DomainUser

class AuthMapper {

//    fun mapToLoginRequest(request: DomainAuthRequest): LoginRequest {
//        return LoginRequest(
//            login = request.phone,
//            password = request.password,
//        )
//    }

    fun mapToRegisterRequest(request: DomainRegisterRequest): RegisterRequest {
        return RegisterRequest(
            phone = request.phone,
            firstName = request.name,
            lastName = request.surname ?: "",
            hashPassword = request.password,
            status = "ACTIVE",
            role = "USER",
            active = true
        )
    }

    fun map(response: NetworkUserResponse): DomainUser {
        return DomainUser(
            id = response.id.toString(),
            phone = response.phone,
            name = response.firstName,
            surname = response.lastName,
            status = response.status
        )
    }

//    fun map(response: NetworkAuthResponse): DomainAuthResponse {
//        return DomainAuthResponse(
//            token = response.token, user = mapUser(response.user)
//        )
//    }
//
//    private fun mapUser(userResponse: NetworkUserResponse): DomainUser {
//        return DomainUser(
//            id = userResponse.id,
//            login = userResponse.login,
//            phone = userResponse.phone,
//            name = userResponse.name,
//            surname = userResponse.surname,
//            status = userResponse.status
//        )
//    }
}