package com.example.cryptoapp.service

import com.example.cryptoapp.CredentialsModel
import com.example.cryptoapp.SessionModel
import com.example.cryptoapp.crypto.TokenModel
import com.example.cryptoapp.movie.PopularPeopleModel
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.movie.TrendingMoviesModel
import retrofit2.http.*

interface TheMovieDBService {

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