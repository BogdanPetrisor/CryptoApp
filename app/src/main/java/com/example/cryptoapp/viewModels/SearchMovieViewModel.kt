package com.example.cryptoapp.viewModels

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.repository.TheMovieDBRepository
import com.example.cryptoapp.adapters.MovieAdapter
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val movieRepository: TheMovieDBRepository
) : ViewModel() {

    private var job: Job = Job()

    private val _movies = MutableLiveData<List<ResultMoviesAndSeriesModel>>()
    val movies: LiveData<List<ResultMoviesAndSeriesModel>>
        get() = _movies

    fun getMoviesByQuery(query: String) {
        job.cancel()
        if (query.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val searchedMoviesPage1 = movieRepository.getSearchMovies(query, 1).results
                val searchedMoviesPage2 = movieRepository.getSearchMovies(query, 2).results
                val searchedMovies = searchedMoviesPage1.plus(searchedMoviesPage2)
                movieRepository.getAllFavoritesMovies().collect { favoriteMovies ->
                    _movies.postValue(searchedMovies.map { movie ->
                        if (favoriteMovies.firstOrNull { it.id == movie.id.toString() } != null) {
                            return@map movie.copy(isFavorite = true)
                        }
                        return@map movie
                    })
                }
            }
        }
    }

    val longClickCallback: (model: ResultMoviesAndSeriesModel) -> Unit = {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            movieRepository.longClickCallback(it)
        }
    }

}