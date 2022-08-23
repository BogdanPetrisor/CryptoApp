package com.example.cryptoapp

import android.app.Application
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseInstance

class MovieApplication : Application() {

    val dao: FavoriteMovieDao by lazy {
        FavoriteMovieDatabaseInstance.getDatabase(this).getMovieDao()
    }
    val movieRepository : TheMovieDBRepository  by lazy {
        TheMovieDBRepository()
    }


}