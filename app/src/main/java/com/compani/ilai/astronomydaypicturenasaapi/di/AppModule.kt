package com.compani.ilai.astronomydaypicturenasaapi.di

import com.compani.ilai.astronomydaypicturenasaapi.BuildConfig
import com.compani.ilai.astronomydaypicturenasaapi.api.NasaApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideNasaApiKeyInterceptor(@Named("nasa_api_key") apiKey: String): Interceptor {
        return Interceptor.invoke { chain ->
            val originalRequest = chain.request()
            val orignalUrl = originalRequest.url

            val newUrl = orignalUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun provideCallFactory(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        nasaApiKeyInterceptor: Interceptor
    ): okhttp3.Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(nasaApiKeyInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    @Named("nasa_api_key")
    fun provideNasaApiKey(): String {
        return BuildConfig.NASA_API_KEY
    }

    @Singleton
    @Provides
    @Named("nasa_base_url")
    fun provideBaseUrl(): String {
        return BuildConfig.NASA_BASE_URL
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        httpLoggingInterceptor: okhttp3.Call.Factory,
        moshiConverterFactory: MoshiConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        @Named("nasa_base_url") baseUrl:String
    ): Retrofit =
        Retrofit.Builder()
            .callFactory(httpLoggingInterceptor)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .baseUrl(baseUrl)
            .build()

    @Singleton
    @Provides
    fun provideNasaApi(retrofit: Retrofit): NasaApi =
        retrofit.create(NasaApi::class.java)
}