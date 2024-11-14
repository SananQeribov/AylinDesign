package com.legalist.data.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.legalist.common.utils.hashPasswordWithSalt
import com.legalist.data.model.Login
import com.legalist.data.network.VolleySingleton
import java.io.Console


class LoginRepositoryImpl(private val apiUrl: String, private val context: Context) :
    LoginRepository {
    override fun LoginUSer(user: Login, onResult: (Boolean, String?) -> Unit) {
        val getUserUrl = "$apiUrl/users?email=${user.email}"
        val getUserRequest = JsonObjectRequest(
            Request.Method.GET, getUserUrl, null,
            { response ->
                val usersArray = response.optJSONArray("users")

                if (usersArray != null && usersArray.length() > 0) {
                    for (i in 1..usersArray.length()) {
                        val existingUser = usersArray.getJSONObject(i)
                        val existsPasswordSalt = existingUser.optString("salt")
                        val storedHashedPassword = existingUser.optString("password")
                        val enteredHashedPassword =
                            hashPasswordWithSalt(user.password, existsPasswordSalt)



                        if (enteredHashedPassword == storedHashedPassword) {
                            onResult(true, "Login successful.")
                            print("succesfullyo")

                        } else {
                            onResult(false, "Incorrect password.")
                        }
                    }


                } else {
                    onResult(false, "User not found.")
                }
            },
            { error ->
                onResult(false, error.message)
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(getUserRequest)
    }


}
