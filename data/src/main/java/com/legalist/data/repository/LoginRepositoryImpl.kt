package com.legalist.data.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.legalist.common.utils.hashPasswordWithSalt
import com.legalist.data.model.Login
import com.legalist.data.network.VolleySingleton
import java.io.Console
import android.util.Log
import com.android.volley.toolbox.JsonArrayRequest

class LoginRepositoryImpl(private val apiUrl: String, private val context: Context) :
    LoginRepository {

    override fun LoginUSer(user: Login, onResult: (Boolean, String?) -> Unit) {
        val getUserUrl = "$apiUrl/users?email=${user.email}"

        val getUserRequest = JsonArrayRequest(
            Request.Method.GET, getUserUrl, null,
            { response ->
                Log.d("LoginRepository", "Response: $response")

                var isLoginSuccessful = false

                if (response.length() > 0) {
                    for (i in 0 until response.length()) {
                        val existingUser = response.getJSONObject(i)
                        val existsPasswordSalt = existingUser.optString("passwordSalt")
                        val existsEmail = existingUser.optString("email")
                        val storedHashedPassword = existingUser.optString("password")

                        Log.d("LoginRepository", "Existing User Salt: $existsPasswordSalt")
                        Log.d("LoginRepository", "Stored Hashed Password: $storedHashedPassword")

                        val enteredHashedPassword = hashPasswordWithSalt(user.password, existsPasswordSalt)
                        Log.d("Current User PAssword", "User password: ${user.password}")
                        Log.d("LoginRepository", "Entered Hashed Password: $enteredHashedPassword")
                        if (user.email == existsEmail){
                            if (enteredHashedPassword == storedHashedPassword) {
                                Log.d("LoginRepository", "Login successful.")
                                isLoginSuccessful = true
                                onResult(true, "Login successful.")
                                break
                            }

                        }

                    }

                    if (!isLoginSuccessful) {
                        onResult(false, "Incorrect password.")
                        Log.d("LoginRepository", "Incorrect password..")
                    }
                } else {
                    onResult(false, "User not found.")
                    Log.d("LoginRepository", "User not found.")
                }
            },
            { error ->
                val errorMessage = error.message ?: "An error occurred"
                Log.e("LoginRepository", "Error: $errorMessage")
                onResult(false, errorMessage)
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(getUserRequest)
    }
}

