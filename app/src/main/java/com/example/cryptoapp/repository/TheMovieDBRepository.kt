package com.example.cryptoapp.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.cryptoapp.service.TheMovieDBService
import com.example.cryptoapp.movie.PopularPeopleModel
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.movie.TrendingMoviesModel
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import kotlinx.coroutines.flow.Flow
import java.lang.NullPointerException
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

    suspend fun getTopRatedMovies(): List<ResultMoviesAndSeriesModel> {
        return service.getTopRatedMovies(apiKey, "en-US", 1).results
    }

    fun getAllFavoritesMovies(): Flow<List<FavoriteMovieDatabaseModel>>{
        return dao.getAllLiveData()
    }

    suspend fun getPopularMovies(): List<ResultMoviesAndSeriesModel> {
        return service.getPopularMovies(apiKey, "en-US", 1).results
    }

    suspend fun getAiringTodayMovies(): List<ResultMoviesAndSeriesModel> {
        return service.getAiringTodayMovies("en-US", 1, apiKey).results
    }

    suspend fun getSearchMovies(query: String, page: Int): TrendingMoviesModel {
        return service.searchMovies(apiKey, "en-US", page, query)
    }

    suspend fun getMovieById(id: Int): ResultMoviesAndSeriesModel {
        return service.getMovieById(id, apiKey)
    }

    suspend fun longClickCallback(model: ResultMoviesAndSeriesModel) {
        try {
            val movie = dao.queryAfterId(model.id.toString())
            dao.update(model.id.toString(), !movie.isFavorite)
        } catch (ex: NullPointerException) {
            dao.insertOne(
                FavoriteMovieDatabaseModel(
                    model.id.toString(),
                    model.name,
                    true,
                    "newMovie"
                )
            )
        }

    }
}

