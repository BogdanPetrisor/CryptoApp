package com.example.cryptoapp

import com.example.cryptoapp.movie.PopularPeopleModel
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.movie.TrendingMoviesModel
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
    ): SessionModel

    @GET("/3/trending/all/day")
    suspend fun getTrendingMoviesAndSeries(
        @Query("api_key") apiKey: String
    ): TrendingMoviesModel

    @GET("3/person/popular")
    suspend fun getPopularPeople(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): PopularPeopleModel

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TrendingMoviesModel

    @GET("3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): TrendingMoviesModel

    @GET("3/tv/airing_today")
    suspend fun getAiringTodayMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): TrendingMoviesModel

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): TrendingMoviesModel

    @GET("3/movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String

    ): ResultMoviesAndSeriesModel


}