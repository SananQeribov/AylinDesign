package com.legalist.data.repository

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.legalist.common.utils.generateSalt
import com.legalist.common.utils.hashPasswordWithSalt
import com.legalist.data.model.User
import com.legalist.data.network.VolleySingleton
import org.json.JSONObject



class UserRepositoryImpl(private val apiUrl: String, private val context: Context) :
    UserRepository {
    override fun registerUser(user: User, onResult: (Boolean, String?) -> Unit) {

        val salt = generateSalt()
        val hashedPassword = hashPasswordWithSalt(user.password, salt)
        val requestBody = JSONObject().apply {
            put("createdAt",user.created)
            put("fullname", user.fullname)
            put("birthday", user.birthday)
            put("email", user.email)
            put("number", user.number)
            put("password", hashedPassword)
            put("passwordSalt",salt)
            put("role", user.role)
        }

        val request = JsonObjectRequest(
            Request.Method.POST, "$apiUrl/users", requestBody,
            { response ->
                Log.d("Response", response.toString())
                val message = if (response.has("message")) response.getString("message") else "No message available"
                onResult(true, message)
            },
            { error ->
                onResult(false, error.message)
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }
}
