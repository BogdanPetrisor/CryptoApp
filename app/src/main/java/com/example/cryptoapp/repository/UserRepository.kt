package com.example.cryptoapp.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.cryptoapp.CredentialsModel
import com.example.cryptoapp.SessionModel
import com.example.cryptoapp.crypto.TokenModel
import com.example.cryptoapp.service.UserService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val service: UserService,
    private val saveSessionId: SharedPreferences
) {

   // private var sessionId: String? = null
    private val apiKey = "96d31308896f028f63b8801331250f03"
    suspend fun requestToken(): TokenModel {
        return service.getToken(apiKey)
    }

    suspend fun login(credentialsModel: CredentialsModel): TokenModel {
        return service.login(apiKey, credentialsModel)
    }

    suspend fun sessionId(tokenModel: TokenModel): SessionModel {
        val session = service.sessionId(apiKey, tokenModel)
        with(saveSessionId.edit()){
            putString("session",session.sessionId)
            apply()
        }
        return session
    }

     fun isUserLogged(): Boolean = !saveSessionId.getString("session",null).isNullOrEmpty()
     fun logoutUser(){
        with(saveSessionId.edit()){
            putString("session",null)
            apply()
        }
    }
    suspend fun deleteSession(sessionModel: SessionModel): SessionModel {
        return service.deleteSession(apiKey, sessionModel)
    }
}