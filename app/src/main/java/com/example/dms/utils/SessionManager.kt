package com.example.dms.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("dms_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val TOKEN_KEY = "TOKEN"
    }

    // --- Token ---
    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN_KEY).apply()
    }

    // --- User ID ---
    fun saveUserId(id: String) {
        sharedPreferences.edit().putString("user_id", id).apply()
    }

    fun getUserId(): String {
        return sharedPreferences.getString("user_id", "") ?: ""
    }

    // --- Email ---
    fun saveUserEmail(email: String) {
        sharedPreferences.edit().putString("user_email", email).apply()
    }

    fun getUserEmail(): String {
        return sharedPreferences.getString("user_email", "") ?: ""
    }

    // --- Phone ---
    fun saveUserPhone(phone: String) {
        sharedPreferences.edit().putString("user_phone", phone).apply()
    }

    fun getUserPhone(): String {
        return sharedPreferences.getString("user_phone", "") ?: ""
    }

    // --- Password ---
    fun saveUserPassword(password: String) {
        sharedPreferences.edit().putString("user_password", password).apply()
    }

    fun getUserPassword(): String {
        return sharedPreferences.getString("user_password", "") ?: ""
    }

    // --- User Name ---
    fun saveUserName(name: String) {
        sharedPreferences.edit().putString("user_name", name).apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString("user_name", "") ?: ""
    }

    // --- User Photo URL ---
    fun saveUserPhoto(url: String) {
        sharedPreferences.edit().putString("user_photo", url).apply()
    }

    fun getUserPhoto(): String {
        return sharedPreferences.getString("user_photo", "") ?: ""
    }
}
