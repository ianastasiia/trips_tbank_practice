package ru.kpfu.itis.android.t_bank_practice_trips.data.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import ru.kpfu.itis.android.t_bank_practice_trips.domain.constants.AppConstants
import javax.inject.Inject

class AuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConstants.PREF_AUTH_KEY)
    private val tokenKey = stringPreferencesKey(AppConstants.PREF_AUTH_TOKEN_KEY)
    private val refreshTokenKey = stringPreferencesKey(AppConstants.PREF_REFRESH_TOKEN_KEY)

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[tokenKey] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(tokenKey)
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[tokenKey]
        }.firstOrNull()
    }

    val isAuthenticated: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[tokenKey] != null
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[refreshTokenKey] = token
        }
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[refreshTokenKey]
        }.firstOrNull()
    }

    suspend fun clearRefreshToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(refreshTokenKey)
        }
    }
}