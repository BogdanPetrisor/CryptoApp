package com.example.cryptoapp.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.example.cryptoapp.CredentialsModel
import com.example.cryptoapp.LoginState
import com.example.cryptoapp.repository.TheMovieDBRepository
import com.example.cryptoapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var job: Job = Job()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState>
        get() = _state

    fun checkIfUserIsLoggedIn() {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            if (userRepository.isUserLogged()) {
                _state.postValue(LoginState.Success)
            }
        }
    }

    fun doLogin() {

        val usernameValue = username.value
        val passwordValue = password.value

        if (usernameValue.isNullOrBlank()) {
            _state.value = LoginState.Error("Username blank;")
            return
        }
        if (passwordValue.isNullOrBlank()) {
            _state.value = LoginState.Error("Password blank;")
            return
        }

        //Cancel any previous login attempt
        job.cancel()

        //Try to login
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.postValue(LoginState.InProgress)

                //Get new token
                val token = userRepository.requestToken()

                //Login
                val login = userRepository.login(
                    CredentialsModel(
                        usernameValue,
                        passwordValue,
                        token.requestToken
                    )
                )
                userRepository.sessionId(login)

                _state.postValue(LoginState.Success)

            } catch (e: HttpException) {
                _state.postValue(LoginState.Error("Incorrect username or password"))
                Log.e("LoginViewModel: ", e.message.toString())
            }
        }
    }
}