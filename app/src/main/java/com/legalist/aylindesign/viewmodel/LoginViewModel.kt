package com.legalist.aylindesign.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.legalist.aylindesign.base.BaseViewModel
import com.legalist.data.model.Login
import com.legalist.data.model.User
import com.legalist.data.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application,
    private val repository: LoginRepository
) : BaseViewModel(application) {

    private val _loginResult = MutableSharedFlow<Pair<Boolean, String>>(replay = 1) // replay 1 ilə sonuncu hadisəni saxlayır
    val loginResult: SharedFlow<Pair<Boolean, String>> get() = _loginResult.asSharedFlow()
     //difference between sharedFlow and stateFlow
   // private val _loginResult = MutableStateFlow<Pair<Boolean, String>?>(null)  // null ilə başladılır
    //    val loginResult: StateFlow<Pair<Boolean, String>?> get() = _loginResult.asStateFlow()


    fun loginUser(login: Login) {
        repository.LoginUSer(login) { isSuccess, message ->
           // updateResult(isSuccess, message)
            //_loginResult.value = Pair(isSuccess, message ?: "No message provided")
            CoroutineScope(Dispatchers.IO).launch {
                _loginResult.emit(Pair(isSuccess, message ?: "No message provided"))
            }

        }
    }
}
