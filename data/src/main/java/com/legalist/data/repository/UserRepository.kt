package com.legalist.data.repository

import com.legalist.data.model.User

interface UserRepository {
    fun registerUser(user: User, onResult: (Boolean, String?) -> Unit)
}