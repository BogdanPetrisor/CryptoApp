package com.example.cryptoapp.di

import android.content.Context
import androidx.room.Room
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseInstance
import com.example.cryptoapp.persistence.FavoriteMovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideMovieDao(database: FavoriteMovieRoomDatabase): FavoriteMovieDao {
        return database.getMovieDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteMovieRoomDatabase {
        return  Room.databaseBuilder(
            context,
            FavoriteMovieRoomDatabase::class.java,
            "favorite_movies_database"
        ).build()
    }

}