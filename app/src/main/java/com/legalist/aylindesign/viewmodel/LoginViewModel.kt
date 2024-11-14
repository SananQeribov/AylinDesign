package com.legalist.aylindesign.viewmodel

import androidx.lifecycle.ViewModel
import com.legalist.data.model.Login
import com.legalist.data.model.User
import com.legalist.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableStateFlow<String?>(null)
    val loginResult: StateFlow<String?> get() = _loginResult.asStateFlow()

    fun loginUser(login: Login) {
        repository.LoginUSer(login) { isSuccess, message ->
            _loginResult.value = if (isSuccess) {
                "Login successful: $message"
            } else {
                "Login failed: $message"
            }
        }
    }
}
