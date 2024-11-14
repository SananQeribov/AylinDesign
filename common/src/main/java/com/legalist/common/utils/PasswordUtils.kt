package com.legalist.common.utils

import org.json.JSONObject
import java.security.SecureRandom
import java.util.Base64
import java.security.MessageDigest


fun isUserAuthenticated(email: String, password: String, user: JSONObject): Boolean {

    if (user.getString("email") == email) {
        // İstifadəçinin saxlanılan `passwordSalt` və `password` dəyərlərini alırıq
        val storedSalt = user.getString("passwordSalt")
        val storedHashedPassword = user.getString("password")

        // İstifadəçinin daxil etdiyi parolu hash-ləyirik
        val hashedInputPassword = hashPasswordWithSalt(password, storedSalt)

        // Əgər hashed parol saxlanılan hashed parolla eynidirsə, giriş uğurludur
        return hashedInputPassword == storedHashedPassword
    }
    return false // Email uyğun gəlmir
}



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