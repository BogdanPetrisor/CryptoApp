package com.example.cryptoapp.persistence

import androidx.room.*

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(data: FavoriteMovieDatabaseModel)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("DELETE FROM $TABLE_NAME WHERE id = :id")
    suspend fun deleteOne(id: String)

    @Update
    suspend fun update(movie: FavoriteMovieDatabaseModel )

    @Query("SELECT * FROM $TABLE_NAME where id = :id")
    suspend fun queryAfterId(id: String): FavoriteMovieDatabaseModel

}