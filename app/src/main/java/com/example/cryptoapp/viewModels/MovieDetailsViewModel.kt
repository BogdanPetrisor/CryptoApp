package com.example.cryptoapp.viewModels

import androidx.lifecycle.*
import com.example.cryptoapp.repository.TheMovieDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: TheMovieDBRepository,
    private val saveStateHandle: SavedStateHandle
) : ViewModel() {


    private var job: Job = Job()
    private val movieId by lazy { saveStateHandle.get<Int>("movieId") }
    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String>
        get() = _overview

    private val _popularity = MutableLiveData<Double>()
    val popularity: LiveData<Double>
        get() = _popularity

    private val _posterImage = MutableLiveData<String>()
    val posterImage: LiveData<String>
        get() = _posterImage

    init {
        movieId.run {
            if(this == null){
                throw IllegalArgumentException("Movie id is require")
            }
            setMovie(this)
        }
    }
    private fun setMovie(id: Int) {
        job.cancel()

        job = viewModelScope.launch(Dispatchers.IO) {
            val movie = movieRepository.getMovieById(id)
            _title.postValue(movie.title)
            _overview.postValue(movie.overview)
            _popularity.postValue(movie.popularity)
            _posterImage.postValue(movie.cardViewImagePath)
        }

    }
}

