package com.legalist.aylindesign.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.legalist.aylindesign.base.BaseViewModel
import com.legalist.data.model.User
import com.legalist.data.repository.LoginRepository
import com.legalist.data.repository.UserRepository

class RegisterViewModel(
    application: Application,
    private val userRepository: UserRepository
) : BaseViewModel(application) {


    fun registerUser(user: User) {
        userRepository.registerUser(user) { success, message ->
            Log.d("LoginResultModel", "ResultModel: $result")
            updateResult(success, message)
        }
    }
}