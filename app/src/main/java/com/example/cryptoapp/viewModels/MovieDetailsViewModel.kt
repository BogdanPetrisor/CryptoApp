package com.example.cryptoapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptoapp.TheMovieDBRepository
import com.example.cryptoapp.movie.ResultMoviesAndSeriesModel
import kotlinx.coroutines.Job

class MovieDetailsViewModel : ViewModel() {

    private val mdbRepo = TheMovieDBRepository()
    private var job: Job = Job()

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _overview = MutableLiveData<String>()
    val overview: LiveData<String>
        get() = _overview

    private val _airDate = MutableLiveData<String>()
    val airDate: LiveData<String>
        get() = _airDate

    private val _popularity = MutableLiveData<Double>()
    val popularity: LiveData<Double>
        get() = _popularity








}