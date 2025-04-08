package com.example.ebisconstore.auth

import androidx.datastore.preferences.core.stringPreferencesKey

object UserToken {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
}