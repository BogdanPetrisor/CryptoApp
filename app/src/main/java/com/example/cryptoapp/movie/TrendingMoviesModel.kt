package com.example.cryptoapp.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingMoviesModel(
    val page: Int = 0,
    val results: List<ResultMoviesAndSeriesModel> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0

)