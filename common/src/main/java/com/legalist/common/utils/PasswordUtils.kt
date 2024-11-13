package com.legalist.common.utils

import java.security.SecureRandom
import java.util.Base64
import java.security.MessageDigest


fun generateSalt(): String {
    val salt = ByteArray(16)
    val secureRandom = SecureRandom()
    secureRandom.nextBytes(salt)
    return Base64.getEncoder().encodeToString(salt)
}


fun hashPasswordWithSalt(password: String, salt: String): String {
    val saltedPassword = password + salt
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(saltedPassword.toByteArray())
    return Base64.getEncoder().encodeToString(hashedBytes)
}