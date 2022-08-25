package com.example.cryptoapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.cryptoapp.service.TheMovieDBService
import com.example.cryptoapp.service.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MovieApiModule {

    @Provides
    fun provideJson() = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }


    @Provides
    fun provideMovieRetrofit(json: Json): TheMovieDBService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TheMovieDBService::class.java)
    }

    @Provides
    fun provideUserRetrofit(json: Json): UserService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("session", Application.MODE_PRIVATE)
    }

}