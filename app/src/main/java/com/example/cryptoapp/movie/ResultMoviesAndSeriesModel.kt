package com.example.cryptoapp.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ResultMoviesAndSeriesModel(
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    val id: Int = 0,
    val title: String = "",
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    @SerialName("poster_path")
    var posterPath: String = "",
    @SerialName("media_type")
    val mediaType: String = "",
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    val popularity: Double = 0.0,
    @SerialName("release_date")
    val releaseDate: String = "",
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    val name: String = "",
    @SerialName("original_name")
    val originalName: String = "",
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @Transient
    val galleryPath: String = "https://image.tmdb.org/t/p/w500$backdropPath",
    @Transient
    val cardViewImagePath: String ="https://image.tmdb.org/t/p/w500$posterPath",
    @Transient
    val isFavorite: Boolean = false
    )
