package com.example.cryptoapp

data class Crypto(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val is_new: Boolean,
    val is_active: Boolean,
    val type: String
){
    override fun toString(): String {
        return "   $type:   #$rank    $name    $symbol    $is_new    $is_active"
    }
}
