package com.example.finalminiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val nameEditText: EditText = findViewById(R.id.edit_text_signup_name)
        val emailEditText: EditText = findViewById(R.id.edit_text_signup_email)
        val passwordEditText: EditText = findViewById(R.id.edit_text_signup_password)
        val signupButton: Button = findViewById(R.id.button_signup)
        val goToLoginTextView: TextView = findViewById(R.id.text_view_go_to_login)

        val prefsManager = SharedPreferencesManager(this)

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Save user details
                prefsManager.registerUser(name, email, password)
                Toast.makeText(this, "Signup successful! Please login.", Toast.LENGTH_LONG).show()
                // Finish this activity to go back to the Login screen
                finish()
            }
        }

        goToLoginTextView.setOnClickListener {
            finish() // Simply close this screen to return to the previous one (Login)
        }
    }
}