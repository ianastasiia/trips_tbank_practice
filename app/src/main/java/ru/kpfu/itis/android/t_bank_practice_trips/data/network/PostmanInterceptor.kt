package ru.kpfu.itis.android.t_bank_practice_trips.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class PostmanInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "x-api-key",
                "PMAK-684701083edeb20001c887c4-c0226c48d5ff53cbfb98d684f1008543ad"
            )
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}