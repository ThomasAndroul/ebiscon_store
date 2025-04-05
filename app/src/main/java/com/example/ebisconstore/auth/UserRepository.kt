package com.example.ebisconstore.auth

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val api: FakeStoreApi
) {
    suspend fun getUsers(): List<User>{
        return withContext(Dispatchers.IO) {
            try {
                val users = api.getUsers()
                Log.d("UserRepository", "API returned users: $users")
                users
            } catch (e: Exception) {
                Log.e("UserRepository", "API call failed: ${e.message}")
                emptyList()
            }
        }
    }
}