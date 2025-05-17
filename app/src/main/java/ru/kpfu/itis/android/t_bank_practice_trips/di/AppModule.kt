package ru.kpfu.itis.android.t_bank_practice_trips.di

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kpfu.itis.android.t_bank_practice_trips.data.repository.SettingsRepositoryImpl
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext appContext: Context,
    ): SettingsRepository = SettingsRepositoryImpl(appContext)
}