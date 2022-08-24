package com.example.cryptoapp.di

import android.content.Context
import androidx.room.Room
import com.example.cryptoapp.persistence.FavoriteMovieDao
import com.example.cryptoapp.persistence.FavoriteMovieDatabaseInstance
import com.example.cryptoapp.persistence.FavoriteMovieRoomDatabase
import com.example.cryptoapp.service.TheMovieDBService
import com.example.cryptoapp.service.UserService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
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
        return Room.databaseBuilder(
            context,
            FavoriteMovieRoomDatabase::class.java,
            "favorite_movies_database"
        ).build()
    }


//    @Provides
//    fun provideJson22() = Json {
//        coerceInputValues = true
//        ignoreUnknownKeys = true
//    }

}