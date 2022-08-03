package com.example.cryptoapp

import com.example.cryptoapp.movie.TrendingModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class TheMovieDBRepository {
    val apiKey = "96d31308896f028f63b8801331250f03"
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    val service = retrofit.create(TheMovieDBService::class.java)

    suspend fun requestToken(): TokenModel {
        return service.getToken(apiKey)
    }

    suspend fun login(credentialsModel: CredentialsModel): TokenModel {
        return service.login(apiKey, credentialsModel)
    }

    suspend fun sessionId(tokenModel: TokenModel): SessionModel {
        return service.sessionId(apiKey, tokenModel)
    }

    suspend fun deleteSession(sessionModel: SessionModel): SessionModel {
        return service.deleteSession(apiKey,sessionModel)
    }

    suspend fun getTrendingMoviesAndSeries(): TrendingModel{
        return service.getTrendingMoviesAndSeries(apiKey)
    }
}