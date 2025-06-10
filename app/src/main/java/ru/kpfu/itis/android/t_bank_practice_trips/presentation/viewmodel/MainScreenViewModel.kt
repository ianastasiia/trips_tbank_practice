package ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Trip
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.TripStatus
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.AuthRepository
import ru.kpfu.itis.android.t_bank_practice_trips.domain.usecase.GetTripsUseCase
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getTripsUseCase: GetTripsUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    fun loadTrips(selectedTab: Int) {
        val status = when (selectedTab) {
            0 -> null
            1 -> TripStatus.ACTIVE
            2 -> TripStatus.COMPLETED
            3 -> TripStatus.PLANNING
            else -> null
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            try {
                val token = authRepository.getToken()

                if (token == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "User not authenticated"
                    )
                    return@launch
                }

                val authHeader = "Bearer $token"

                val trips = if (status != null) {
                    getTripsUseCase(
                        token = authHeader,
                        status = status
                    )
                } else {
                    val active = getTripsUseCase(authHeader, TripStatus.ACTIVE)
                    val completed = getTripsUseCase(authHeader, TripStatus.COMPLETED)
                    active + completed
                }

                _state.value = _state.value.copy(
                    trips = trips,
                    isLoading = false,
                    error = null
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error",
                    isLoading = false
                )
            }
        }
    }
}

data class MainScreenState(
    val trips: List<Trip> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)