package org.exercise.remitconnectclaude.util

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val PREF_USER_BALANCE = "PREF_USER_BALANCE"
        const val PREF_USER_NAME = "PREF_USER_NAME"
        const val PREF_RECIPIENT_ID = "PREF_RECIPIENT_ID"
    }

    fun getData(key: String, defaultValue: String) =
        sharedPreferences.getString(key, defaultValue) ?: defaultValue

    fun saveData(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getInt(key: String, defaultValue: Int) =
        sharedPreferences.getInt(key, defaultValue)

    fun saveInt(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }
}