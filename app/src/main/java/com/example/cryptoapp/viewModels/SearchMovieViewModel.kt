package com.example.cryptoapp.viewModels

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.MovieApplication
import com.example.cryptoapp.TheMovieDBRepository
import com.example.cryptoapp.movie.MovieAdapter
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
    private val dao: FavoriteMovieDao,
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
                _movies.postValue(searchedMovies)
            }
        }
    }

    val longClickCallback: (model: ResultMoviesAndSeriesModel, view: RecyclerView) -> Unit =
        { model, view->
            viewModelScope.launch(Dispatchers.IO) {
                if (model.isFavorite) {
                    dao.deleteOne(model.id.toString())
                } else {
                    dao.insertOne(
                        FavoriteMovieDatabaseModel(
                            model.id.toString(),
                            model.name
                        )
                    )
                }
            }
            (view.adapter as? MovieAdapter)?.modifyOneElement(model)
        }

}