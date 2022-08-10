package com.example.cryptoapp.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME = "favorites_movies"

@Entity(tableName = TABLE_NAME)
data class FavoriteMovieDatabaseModel(
    @ColumnInfo
    @PrimaryKey
    var id: String = "",
    @ColumnInfo
    var name: String = "",

)