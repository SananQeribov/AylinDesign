package com.legalist.data.repository

import com.legalist.data.model.Login

//https://66a8396353c13f22a3d21b48.mockapi.io/api/v1/users
interface LoginRepository{
    fun LoginUSer(user: Login, onResult: (Boolean, String?) -> Unit)
}