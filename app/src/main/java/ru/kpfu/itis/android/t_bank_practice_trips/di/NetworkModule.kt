package ru.kpfu.itis.android.t_bank_practice_trips.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.android.t_bank_practice_trips.BuildConfig
import ru.kpfu.itis.android.t_bank_practice_trips.data.api.AuthApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.api.TripApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.auth.AuthManager
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.AuthMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.TripMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.network.AuthInterceptor
import ru.kpfu.itis.android.t_bank_practice_trips.data.network.PostmanInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideAuthInterceptor(authManager: AuthManager): AuthInterceptor {
        return AuthInterceptor(authManager)
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create()

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        postmanInterceptor: PostmanInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(postmanInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://c678f6d9-fb3b-4f00-8355-6e07bb28fd00.mock.pstmn.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthMapper(): AuthMapper = AuthMapper()

    @Provides
    @Singleton
    fun provideTripApiService(retrofit: Retrofit): TripApiService {
        return retrofit.create(TripApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTripMapper(): TripMapper = TripMapper()

    @Provides
    @Singleton
    fun providePostmanInterceptor(): PostmanInterceptor {
        return PostmanInterceptor()
    }

}