package ru.kpfu.itis.android.t_bank_practice_trips.data.mapper

import ru.kpfu.itis.android.t_bank_practice_trips.data.request.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.data.response.UserResponse as NetworkUserResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest as DomainRegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.User as DomainUser

class AuthMapper {

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
            id = response.id,
            phone = response.phone,
            name = response.firstName,
            surname = response.lastName,
            status = response.status
        )
    }
}