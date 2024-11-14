package com.legalist.data.repository

import com.legalist.data.model.User

//https://66a8396353c13f22a3d21b48.mockapi.io/api/v1/users
interface UserRepository {
    fun registerUser(user: User, onResult: (Boolean, String?) -> Unit)
}