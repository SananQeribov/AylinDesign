package com.legalist.aylindesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.legalist.data.model.User
import com.legalist.data.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<String>()
    val registerResult: LiveData<String> = _registerResult

    fun registerUser(user: User) {
        userRepository.registerUser(user) { success, message ->
            if (success) {
                _registerResult.value = "Registration successful!"
            } else {
                _registerResult.value = "Registration failed: $message"
            }
        }
    }
}