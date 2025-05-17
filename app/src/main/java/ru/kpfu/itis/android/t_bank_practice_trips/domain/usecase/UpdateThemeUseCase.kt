package ru.kpfu.itis.android.t_bank_practice_trips.domain.usecase

import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateThemeUseCase @Inject constructor(private val repository: SettingsRepository) {
    suspend operator fun invoke(isEnabled: Boolean) = repository.updateTheme(isEnabled)
}