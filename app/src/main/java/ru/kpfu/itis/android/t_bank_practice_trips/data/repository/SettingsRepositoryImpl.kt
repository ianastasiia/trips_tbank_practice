package ru.kpfu.itis.android.t_bank_practice_trips.data.repository

import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import ru.kpfu.itis.android.t_bank_practice_trips.data.datasource.PreferencesManager
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.AppLanguage.Companion.toLanguageCode
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Settings
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : SettingsRepository {

    private val _languageUpdates = MutableSharedFlow<String>(extraBufferCapacity = 1)
    override val languageUpdates: SharedFlow<String> = _languageUpdates
    private val _themeUpdates = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    override val themeUpdates: SharedFlow<Boolean> = _themeUpdates

    override suspend fun getSettings(): Settings {
        return preferencesManager.getSettings().first()
    }

    override suspend fun updateTheme(isEnabled: Boolean) {
        preferencesManager.updateTheme(isEnabled)
        AppCompatDelegate.setDefaultNightMode(
            if (isEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        _themeUpdates.emit(isEnabled)
    }

    override suspend fun updateLanguage(language: String) {
        val langCode = language.toLanguageCode()
        preferencesManager.updateLanguage(langCode)
        _languageUpdates.emit(langCode)
    }

//    private fun updateAppLocale(language: String) {
//        val config = Configuration(appContext.resources.configuration).apply {
//            setLocale(Locale(language))
//        }
//
//        appContext.createConfigurationContext(config)
//    }
}