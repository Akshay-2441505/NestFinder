package com.example.finalminiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefsManager = SharedPreferencesManager(this)

        // Check if the user is logged in
        if (prefsManager.isLoggedIn()) {
            // If logged in, go to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            // If not logged in, go to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Finish this activity so the user can't press "back" to get to it
        finish()
    }
}