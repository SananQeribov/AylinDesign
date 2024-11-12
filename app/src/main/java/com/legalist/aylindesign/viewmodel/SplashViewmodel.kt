package com.legalist.aylindesign.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.legalist.aylindesign.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewmodel(application: Application) : BaseViewModel(application) {
    private val _splashTime = MutableStateFlow(false)
    var splashTime = _splashTime.asStateFlow()
      fun splash ()  {
         viewModelScope.launch {
             delay(6000) // 6 saniyə
             _splashTime.value = true

         }
     }


}