package ru.kpfu.itis.android.t_bank_practice_trips.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.android.t_bank_practice_trips.data.api.AuthApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.api.TripApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.auth.AuthManager
import ru.kpfu.itis.android.t_bank_practice_trips.data.datasource.PreferencesManager
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.AuthMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.TripMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.repository.AuthRepositoryImpl
import ru.kpfu.itis.android.t_bank_practice_trips.data.repository.SettingsRepositoryImpl
import ru.kpfu.itis.android.t_bank_practice_trips.data.repository.TripRepositoryImpl
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.AuthRepository
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.TripRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext context: Context
    ): PreferencesManager = PreferencesManager(context)

    @Provides
    @Singleton
    fun provideAuthManager(
        @ApplicationContext context: Context
    ): AuthManager = AuthManager(context)

    @Provides
    @Singleton
    fun provideSettingsRepository(
        preferencesManager: PreferencesManager,
    ): SettingsRepository = SettingsRepositoryImpl(
        preferencesManager = preferencesManager
    )

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: AuthApiService, mapper: AuthMapper, authManager: AuthManager
    ): AuthRepository = AuthRepositoryImpl(
        apiService = apiService, mapper = mapper, authManager = authManager
    )

    @Provides
    @Singleton
    fun provideTripRepository(
        apiService: TripApiService,
        mapper: TripMapper
    ): TripRepository = TripRepositoryImpl(
        api = apiService,
        mapper = mapper
    )

}