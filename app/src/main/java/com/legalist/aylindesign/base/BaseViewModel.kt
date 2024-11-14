package com.legalist.aylindesign.base


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val _result = MutableLiveData<Pair<Boolean, String>>()
    val result: LiveData<Pair<Boolean, String>> get() = _result

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun updateResult(isSuccess: Boolean, message: String?) {
        _result.value = Pair(isSuccess, message ?: "No message provided")
    }
}

