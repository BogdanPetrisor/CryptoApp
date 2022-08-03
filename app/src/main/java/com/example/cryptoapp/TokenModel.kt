package com.example.cryptoapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenModel(
    val success: Boolean = false,
    @SerialName("expires_at")
    val expiresAt: String = "",
    @SerialName("request_token")
    val requestToken: String=""
)