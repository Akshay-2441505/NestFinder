package com.example.finalminiproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {

    private lateinit var prefsManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefsManager = SharedPreferencesManager(this)

        val toolbar: Toolbar = findViewById(R.id.settings_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"

        val themeRadioGroup: RadioGroup = findViewById(R.id.theme_radio_group)
        val radioLight: RadioButton = findViewById(R.id.radio_light)
        val radioDark: RadioButton = findViewById(R.id.radio_dark)
        val saveButton: Button = findViewById(R.id.save_settings_button)

        // Load the saved preference and check the correct radio button
        when (prefsManager.getThemePreference()) {
            "Dark" -> radioDark.isChecked = true
            else -> radioLight.isChecked = true
        }

        saveButton.setOnClickListener {
            // Determine the selected theme
            val selectedTheme = if (radioDark.isChecked) "Dark" else "Light"

            // Save the preference
            prefsManager.saveThemePreference(selectedTheme)

            // Prepare the result intent to send back to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("RESULT_MESSAGE", "Settings Saved!")
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close this activity and return to the previous one
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}