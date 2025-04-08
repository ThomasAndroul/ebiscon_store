package com.example.ebisconstore.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class UserManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val dataStore = context.dataStore

    val tokenFlow = dataStore.data.map { prefs ->
        prefs[UserToken.AUTH_TOKEN]
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { it[UserToken.AUTH_TOKEN] = token }
    }

    suspend fun clearToken() {
        dataStore.edit { it.remove(UserToken.AUTH_TOKEN) }
    }
}