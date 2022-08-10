package com.example.cryptoapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteMovieDatabaseModel::class],
    version = 1
)

abstract class FavoriteMovieRoomDatabase : RoomDatabase() {

    abstract fun getMovieDao(): FavoriteMovieDao


}