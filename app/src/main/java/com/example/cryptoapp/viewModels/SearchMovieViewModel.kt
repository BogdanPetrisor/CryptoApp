package com.example.cryptoapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.ShipsQuery
import com.example.cryptoapp.TheMovieDBRepository
import com.example.cryptoapp.apolloClient
import com.example.cryptoapp.movie.MovieAdapter
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchMovieViewModel : ViewModel() {

    private val mdbRepo = TheMovieDBRepository()
    private var job: Job = Job()

    private val _movies = MutableLiveData<List<ResultMoviesAndSeriesModel>>()
    val movies: LiveData<List<ResultMoviesAndSeriesModel>>
        get() = _movies

    fun getMoviesByQuery(query: String) {
        job.cancel()
        if (query.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val searchedMoviesPage1 = mdbRepo.getSearchMovies(query, 1).results
                val searchedMoviesPage2 = mdbRepo.getSearchMovies(query, 2).results
                val searchedMovies = searchedMoviesPage1.plus(searchedMoviesPage2)
                _movies.postValue(searchedMovies)

            }
        }
    }

    val callback: (model: ResultMoviesAndSeriesModel, view: RecyclerView, dao: FavoriteMovieDao) -> Unit =
        { model, view, dao ->
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