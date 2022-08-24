package com.example.cryptoapp

import android.app.Application
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseInstance
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieApplication : Application() {}