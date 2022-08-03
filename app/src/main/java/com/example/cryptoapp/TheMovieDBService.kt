package com.example.cryptoapp

import com.example.cryptoapp.movie.TrendingModel
import retrofit2.http.*

interface TheMovieDBService {
    @GET("/3/authentication/token/new")
    suspend fun getToken(
        @Query("api_key") apiKey: String
    ): TokenModel

    @POST("/3/authentication/token/validate_with_login")
    suspend fun login(
        @Query("api_key") apiKey: String,
        @Body credentialsModel: CredentialsModel
    ): TokenModel

    @POST("/3/authentication/session/new")
    suspend fun sessionId(
        @Query("api_key") apiKey: String,
        @Body tokenModel: TokenModel
    ): SessionModel

    @HTTP(method = "DELETE", path = "/3/authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Query("api_key") apiKey: String,
        @Body sessionModel: SessionModel
    ):SessionModel

    @GET("/3/trending/all/day")
    suspend fun getTrendingMoviesAndSeries(
        @Query("api_key") apiKey: String
    ):TrendingModel
}