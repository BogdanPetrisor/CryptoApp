package com.example.cryptoapp.viewModels

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.MovieApplication
import com.example.cryptoapp.ShipsQuery
import com.example.cryptoapp.TheMovieDBRepository
import com.example.cryptoapp.apolloClient
import com.example.cryptoapp.movie.MovieAdapter
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import com.example.cryptoapp.movie.ResultPopularPeopleModel
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val dao: FavoriteMovieDao,
    private val movieRepository: TheMovieDBRepository
) : ViewModel() {
    private var job: Job = Job()
    init {
        getMovies()
    }

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
            _topRatedMovies.postValue(movieRepository.getTopRatedMovies().results)
            _popularMovies.postValue(movieRepository.getPopularMovies().results)
            _airingTodayMovies.postValue(movieRepository.getAiringTodayMovies().results)
            _movieStars.postValue(movieRepository.getPopularPeople().results)
            val response =
                apolloClient.query(ShipsQuery()).execute()
            _ships.postValue(response.data?.launchLatest?.ships as List<ShipsQuery.Ship>)
            _galleryMovies.postValue(movieRepository.getTrendingMoviesAndSeries().results)
        }
    }

    val longClickCallback: (model: ResultMoviesAndSeriesModel, view: RecyclerView) -> Unit =
        { model, view ->
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

class HomeScreenViewModelFactory(private val application: MovieApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeScreenViewModel(
            application.dao,
            application.movieRepository,
        ) as T
    }
}



