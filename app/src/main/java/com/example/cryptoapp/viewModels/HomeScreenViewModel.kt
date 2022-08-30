package com.example.cryptoapp.viewModels

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.exception.ApolloHttpException
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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.NullPointerException
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

    private val topRatedMovies = flow {emit(movieRepository.getTopRatedMovies()) }
    val topRatedMoviesWithFavorites: StateFlow<List<ResultMoviesAndSeriesModel>> =
        topRatedMovies.combine(movieRepository.getAllFavoritesMovies()) { topRatedMovies, favoriteMovies ->
            topRatedMovies.map { movie ->
                if (favoriteMovies.firstOrNull { it.id == movie.id.toString() } != null) {
                    return@map movie.copy(isFavorite = true)
                }
                return@map movie
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val popularMovies = flow {emit(movieRepository.getPopularMovies()) }
    val popularMoviesWithFavorites: StateFlow<List<ResultMoviesAndSeriesModel>> =
        popularMovies.combine(movieRepository.getAllFavoritesMovies()) { popularMovies, favoriteMovies ->
            popularMovies.map { movie ->
                if (favoriteMovies.firstOrNull { it.id == movie.id.toString() } != null) {
                    return@map movie.copy(isFavorite = true)
                }
                return@map movie
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val airingTodayMovies = flow{emit(movieRepository.getAiringTodayMovies()) }
    val airingTodayMoviesWithFavorites: StateFlow<List<ResultMoviesAndSeriesModel>> =
        airingTodayMovies.combine(movieRepository.getAllFavoritesMovies()){airingTodayMovies, favoriteMovies ->
            airingTodayMovies.map { movie ->
                if (favoriteMovies.firstOrNull { it.id == movie.id.toString() } != null) {
                    return@map movie.copy(isFavorite = true)
                }
                return@map movie
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
            try {
                _movieStars.postValue(movieRepository.getPopularPeople().results)
            } catch (ex: NullPointerException) {
            }
            try {
                val response =
                    apolloClient.query(ShipsQuery()).execute()
                _ships.postValue(response.data?.launchLatest?.ships as List<ShipsQuery.Ship>)
            } catch (ex: ApolloHttpException) {
            }
            _galleryMovies.postValue(movieRepository.getTrendingMoviesAndSeries().results)
        }
    }
    init {
        getMovies()
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




