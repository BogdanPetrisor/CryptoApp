package com.example.cryptoapp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionModel(
    val success: Boolean = false,
    @SerialName("session_id")
    val sessionId: String = "",
    val failure: Boolean = true,
    @SerialName("status_code")
    val statusCode: Int = 1,
    @SerialName("status_message")
    val status_message: String = ""
)