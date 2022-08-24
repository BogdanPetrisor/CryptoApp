package com.example.cryptoapp.crypto

import com.example.cryptoapp.crypto.StatsModel

data class LinksExtendedModel(
    val url: String = "",
    val type: String = "",
    val stats: StatsModel = StatsModel()
)