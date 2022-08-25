package com.example.cryptoapp.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(data: FavoriteMovieDatabaseModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: FavoriteMovieDatabaseModel)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteOne(id: String)

    @Query("UPDATE $TABLE_NAME SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun update(id: String, isFavorite: Boolean)

    @Query("SELECT * FROM $TABLE_NAME where id = :id")
    suspend fun queryAfterId(id: String): FavoriteMovieDatabaseModel

    @Query("SELECT isFavorite FROM $TABLE_NAME where id = :id")
    suspend fun findMovieById(id: String): Boolean

    @Query("SELECT * FROM $TABLE_NAME where isFavorite = 1")
    fun getAllLiveData(): Flow<List<FavoriteMovieDatabaseModel>>


}