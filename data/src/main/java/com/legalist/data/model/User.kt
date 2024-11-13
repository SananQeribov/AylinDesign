package com.legalist.data.model

data class User(
    val created: String,
    val fullname: String,
    val birthday: String,
    val email: String,
    val number: String,
    val password: String,
    val role: String,
    val isDeleted: Boolean,
    val id: String
)