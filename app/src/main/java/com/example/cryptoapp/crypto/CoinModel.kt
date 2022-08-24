package com.example.cryptoapp.crypto

data class CoinModel(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isNew: Boolean,
    val isActive: Boolean,
    val type: String,
) {
    override fun toString(): String {
        return "  $type:   #$rank    $name    $symbol    $isNew    $isActive"
    }
}
