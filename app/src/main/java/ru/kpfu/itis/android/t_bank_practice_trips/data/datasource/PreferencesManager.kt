package ru.kpfu.itis.android.t_bank_practice_trips.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.android.t_bank_practice_trips.domain.constants.AppConstants
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.Settings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = AppConstants.PREF_SETTINGS_KEY
    )

    companion object {
        val THEME_DARK = booleanPreferencesKey(AppConstants.PREF_IS_THEME_DARK_KEY)
        val LANGUAGE = stringPreferencesKey(AppConstants.PREF_LANGUAGE_KEY)
    }

    fun getSettings(): Flow<Settings> = context.dataStore.data.map { prefs ->
            Settings(
                isDarkTheme = prefs[THEME_DARK] ?: AppConstants.PREF_IS_THEME_DARK_VAL,
                language = prefs[LANGUAGE] ?: AppConstants.PREF_LANGUAGE_DEF_VAL
            )
        }

    suspend fun updateTheme(isEnabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[THEME_DARK] = isEnabled
        }
    }

    suspend fun updateLanguage(langCode: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE] = langCode
        }
    }
}