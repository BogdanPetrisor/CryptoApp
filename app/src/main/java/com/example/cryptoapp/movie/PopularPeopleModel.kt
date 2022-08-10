package com.example.cryptoapp.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularPeopleModel(
    val page: Int = 0,
    val results: List<ResultPopularPeopleModel> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0
)
