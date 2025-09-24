package com.example.finalminiproject

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("NestFinderPrefs", Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    // Keys for storing data
    private val KEY_FAVORITES = "FAVORITE_PROPERTIES"
    private val KEY_THEME = "THEME_PREF"
    private val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
    private val KEY_USER_EMAIL = "USER_EMAIL"
    private val KEY_USER_PASSWORD = "USER_PASSWORD"
    private val KEY_USER_NAME = "USER_NAME"

    // --- User Session Management ---

    fun registerUser(name: String, email: String, password: String) {
        editor.putString(KEY_USER_NAME, name)
        editor.putString(KEY_USER_EMAIL, email)
        editor.putString(KEY_USER_PASSWORD, password) // Note: In a real app, never store plain text passwords!
        editor.apply()
    }

    fun loginUser(email: String, password: String): Boolean {
        val savedEmail = prefs.getString(KEY_USER_EMAIL, null)
        val savedPassword = prefs.getString(KEY_USER_PASSWORD, null)
        return email.equals(savedEmail, ignoreCase = true) && password == savedPassword
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logoutUser() {
        editor.putBoolean(KEY_IS_LOGGED_IN, false)
        editor.apply()
        // We could also clear favorites here if we wanted: editor.remove(KEY_FAVORITES).apply()
    }

    fun getUserName(): String {
        return prefs.getString(KEY_USER_NAME, "Guest") ?: "Guest"
    }

    // --- Favorites Management ---

    fun addFavorite(propertyId: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.add(propertyId)
        editor.putStringSet(KEY_FAVORITES, favorites).apply()
    }

    fun removeFavorite(propertyId: String) {
        val favorites = getFavorites().toMutableSet()
        favorites.remove(propertyId)
        editor.putStringSet(KEY_FAVORITES, favorites).apply()
    }

    fun isFavorite(propertyId: String): Boolean {
        return getFavorites().contains(propertyId)
    }

    fun getFavorites(): Set<String> {
        return prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    // --- Theme Management ---

    fun saveThemePreference(theme: String) {
        editor.putString(KEY_THEME, theme).apply()
    }

    fun getThemePreference(): String {
        return prefs.getString(KEY_THEME, "Light") ?: "Light"
    }

    // In SharedPreferencesManager.kt, inside the class

    fun getUserEmail(): String {
        return prefs.getString(KEY_USER_EMAIL, "user@example.com") ?: "user@example.com"
    }

}