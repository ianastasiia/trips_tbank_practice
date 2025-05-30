package ru.kpfu.itis.android.t_bank_practice_trips.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kpfu.itis.android.t_bank_practice_trips.data.api.AuthApiService
import ru.kpfu.itis.android.t_bank_practice_trips.data.auth.AuthManager
import ru.kpfu.itis.android.t_bank_practice_trips.data.mapper.AuthMapper
import ru.kpfu.itis.android.t_bank_practice_trips.data.network.AuthInterceptor
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
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor)
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
//                else HttpLoggingInterceptor.Level.NONE
//            })
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.t-travel.com/v1/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthMapper(): AuthMapper = AuthMapper()
}