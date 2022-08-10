package com.example.cryptoapp.persistence

import android.content.Context
import androidx.room.Room

object FavoriteMovieDatabaseInstance {
    private const val FAVORITES_MOVIES_DATABASE_TAG = "favorite_movies_database"
    private var INSTANCE: FavoriteMovieRoomDatabase? = null
    fun getDatabase(context: Context): FavoriteMovieRoomDatabase? {
        if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        context,
                        FavoriteMovieRoomDatabase::class.java,
                        FAVORITES_MOVIES_DATABASE_TAG
                    ).build()
        }
        return INSTANCE
    }
}
