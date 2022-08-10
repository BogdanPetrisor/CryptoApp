package com.example.cryptoapp

import com.example.cryptoapp.movie.PopularPeopleModel
import com.example.cryptoapp.movie.TrendingMoviesModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class TheMovieDBRepository {
    val apiKey = "96d31308896f028f63b8801331250f03"
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
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
        return service.deleteSession(apiKey, sessionModel)
    }

    suspend fun getTrendingMoviesAndSeries(): TrendingMoviesModel {
        return service.getTrendingMoviesAndSeries(apiKey)
    }

    suspend fun getPopularPeople(): PopularPeopleModel {
        return service.getPopularPeople(apiKey, "en-US", 1)
    }

    suspend fun getTopRatedMovies(): TrendingMoviesModel {
        return service.getTopRatedMovies(apiKey, "en-US", 1)
    }

    suspend fun getPopularMovies(): TrendingMoviesModel {
        return service.getPopularMovies(apiKey, "en-US", 1)
    }

    suspend fun getAiringTodayMovies(): TrendingMoviesModel {
        return service.getAiringTodayMovies("en-US", 1, apiKey)
    }

    suspend fun getSearchMovies(query: String, page: Int): TrendingMoviesModel{
        return service.searchMovies(apiKey,"en-US",page, query)
    }


}