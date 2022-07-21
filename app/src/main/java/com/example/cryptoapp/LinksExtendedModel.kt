package com.example.cryptoapp

data class LinksExtendedModel(
    val url: String = "",
    val type: String = "",
    val stats: StatsModel = StatsModel()
)