package com.example.authassignment

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPrefManager(val context: Context) {
    var EMAIL = "EMAIL"
    var PASSWORD = "PASSWORD"
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "MySharedPreference",
        MODE_PRIVATE
    )

    fun getUserInfo(): ArrayList<String> {
        return arrayListOf(sharedPreferences.getString(EMAIL, "")!!, sharedPreferences.getString(PASSWORD, "")!!)
    }

    fun setUserInfo(username: String, password : String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(EMAIL, username)
        editor.putString(PASSWORD, password)
        editor.apply()
    }
}