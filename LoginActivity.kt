package com.example.finalminiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText: EditText = findViewById(R.id.edit_text_login_email)
        val passwordEditText: EditText = findViewById(R.id.edit_text_login_password)
        val loginButton: Button = findViewById(R.id.button_login)
        val goToSignupTextView: TextView = findViewById(R.id.text_view_go_to_signup)

        val prefsManager = SharedPreferencesManager(this)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (prefsManager.loginUser(email, password)) {
                    // Login successful
                    prefsManager.setLoggedIn(true)
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                    // Go to the main part of the app
                    val intent = Intent(this, MainActivity::class.java)
                    // These flags clear the activity history, so the user can't press "back" to get to the login screen
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    // Login failed
                    Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        goToSignupTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}