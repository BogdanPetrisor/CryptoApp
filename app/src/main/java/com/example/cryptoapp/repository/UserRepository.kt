package com.example.cryptoapp.repository

import com.example.cryptoapp.CredentialsModel
import com.example.cryptoapp.SessionModel
import com.example.cryptoapp.crypto.TokenModel
import com.example.cryptoapp.service.UserService
import javax.inject.Singleton

@Singleton
class UserRepository (val service: UserService) {

    private val apiKey = "96d31308896f028f63b8801331250f03"
    suspend fun requestToken(): TokenModel {
        return service.getToken(apiKey)
    }

    suspend fun login(credentialsModel: CredentialsModel): TokenModel {
        return service.login(apiKey, credentialsModel)
    }

    suspend fun sessionId(tokenModel: TokenModel): SessionModel {
        return service.sessionId(apiKey, tokenModel)
    }

    suspend fun deleteSession(sessionModel: SessionModel): SessionModel {
        return service.deleteSession(apiKey, sessionModel)
    }
}