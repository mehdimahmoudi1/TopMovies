package com.example.topmovies.utils

import android.content.Context
import android.content.SharedPreferences
import android.media.session.MediaSession
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStore @Inject constructor(@ApplicationContext val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.USER_DATA_STORE)
        val userToken = stringPreferencesKey(Constants.USER_TOKEN)
    }

    suspend fun saveUserToken(token: String) {
        context.dataStore.edit {
            it[userToken] = token
        }
    }

    fun getUserToken() = context.dataStore.data.map {
        it[userToken] ?: ""
    }
}