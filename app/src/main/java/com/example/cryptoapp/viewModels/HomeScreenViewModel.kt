package com.example.cryptoapp.viewModels

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.ShipsQuery
import com.example.cryptoapp.repository.TheMovieDBRepository
import com.example.cryptoapp.apolloClient
import com.example.cryptoapp.adapters.MovieAdapter
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.movie.ResultPopularPeopleModel
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import com.example.cryptoapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieRepository: TheMovieDBRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private var job: Job = Job()

    init {
        getMovies()
    }

    private val _favoritesMovies = MutableLiveData<List<FavoriteMovieDatabaseModel>>()
    val favoritesMovies: LiveData<List<FavoriteMovieDatabaseModel>>
        get() = _favoritesMovies

    private val _topRatedMovies = MutableLiveData<List<ResultMoviesAndSeriesModel>>()
    val topRatedMovies: LiveData<List<ResultMoviesAndSeriesModel>>
        get() = _topRatedMovies

    private val _popularMovies = MutableLiveData<List<ResultMoviesAndSeriesModel>>()
    val popularMovies: LiveData<List<ResultMoviesAndSeriesModel>>
        get() = _popularMovies

    private val _airingTodayMovies = MutableLiveData<List<ResultMoviesAndSeriesModel>>()
    val airingTodayMovies: LiveData<List<ResultMoviesAndSeriesModel>>
        get() = _airingTodayMovies

    private val _movieStars = MutableLiveData<List<ResultPopularPeopleModel>>()
    val movieStars: LiveData<List<ResultPopularPeopleModel>>
        get() = _movieStars

    private val _ships = MutableLiveData<List<ShipsQuery.Ship>>()
    val ships: LiveData<List<ShipsQuery.Ship>>
        get() = _ships

    private val _galleryMovies = MutableLiveData<List<ResultMoviesAndSeriesModel>>()
    val galleryMovies: LiveData<List<ResultMoviesAndSeriesModel>>
        get() = _galleryMovies

    private fun getMovies() {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _movieStars.postValue(movieRepository.getPopularPeople().results)
            val response =
                apolloClient.query(ShipsQuery()).execute()
            _ships.postValue(response.data?.launchLatest?.ships as List<ShipsQuery.Ship>)
            _galleryMovies.postValue(movieRepository.getTrendingMoviesAndSeries().results)
            movieRepository.getAllFavoritesMovies().collect { favoritesMovies ->

                _topRatedMovies.postValue(movieRepository.getTopRatedMovies().map { movie ->
                    if (favoritesMovies.firstOrNull { it.id == movie.id.toString() } != null) {
                        return@map movie.copy(isFavorite = true)
                    }
                    return@map movie
                })

                _popularMovies.postValue(movieRepository.getPopularMovies().map { movie ->
                    if (favoritesMovies.firstOrNull { it.id == movie.id.toString() } != null) {
                        return@map movie.copy(isFavorite = true)
                    }
                    return@map movie
                })

                _airingTodayMovies.postValue(movieRepository.getAiringTodayMovies().map { movie ->
                    if (favoritesMovies.firstOrNull { it.id == movie.id.toString() } != null) {
                        return@map movie.copy(isFavorite = true)
                    }
                    return@map movie
                })
            }

        }
    }

    fun doLogout() {
        userRepository.logoutUser()
    }


    val longClickCallback: (model: ResultMoviesAndSeriesModel) -> Unit = {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            movieRepository.longClickCallback(it)
        }
    }


}




