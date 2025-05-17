package ru.kpfu.itis.android.t_bank_practice_trips.domain.usecase

import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Settings
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(private val repository: SettingsRepository) {
    suspend operator fun invoke(): Settings = repository.getSettings()
}