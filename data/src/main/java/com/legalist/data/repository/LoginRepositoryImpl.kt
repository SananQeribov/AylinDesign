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

        // Use JsonArrayRequest because the response is an array
        val getUserRequest = JsonArrayRequest(
            Request.Method.GET, getUserUrl, null,
            { response ->

                Log.d("LoginRepository", "Response: $response")


                if (response.length() > 0) {

                    for (i in 0 until response.length()) {
                        val existingUser = response.getJSONObject(i)
                        val existsPasswordSalt = existingUser.optString("passwordSalt")
                        val storedHashedPassword = existingUser.optString("password")


                        Log.d("LoginRepository", "Existing User Salt: $existsPasswordSalt")
                        Log.d("LoginRepository", "Stored Hashed Password: $storedHashedPassword")

                        val enteredHashedPassword =
                            hashPasswordWithSalt(user.password, existsPasswordSalt)

                        // Log the entered hashed password for debugging
                        Log.d("LoginRepository", "Entered Hashed Password: $enteredHashedPassword")

                        if (enteredHashedPassword == storedHashedPassword) {
                            Log.d("LoginRepository", "Login successful.")
                            onResult(true, "Login successful.")
                            return@JsonArrayRequest
                        }
                    }
                    onResult(false, "Incorrect password.")
                } else {
                    onResult(false, "User not found.")
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
