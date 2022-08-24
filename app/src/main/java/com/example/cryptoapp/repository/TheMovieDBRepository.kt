package com.example.cryptoapp.repository

import com.example.cryptoapp.service.TheMovieDBService
import com.example.cryptoapp.movie.PopularPeopleModel
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.movie.TrendingMoviesModel
import com.example.cryptoapp.persistence.FavoriteMovieDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TheMovieDBRepository @Inject constructor(
    private val service: TheMovieDBService,
    private val dao: FavoriteMovieDao

) {
    val apiKey = "96d31308896f028f63b8801331250f03"

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

    suspend fun getSearchMovies(query: String, page: Int): TrendingMoviesModel {
        return service.searchMovies(apiKey, "en-US", page, query)
    }

    suspend fun getMovieById(id: String): ResultMoviesAndSeriesModel {
        return service.getMovieById(id, apiKey)
    }

}