package com.example.finalminiproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefsManager = SharedPreferencesManager(requireContext())

        // Find the views
        val nameTextView: TextView = view.findViewById(R.id.profile_name)
        val emailTextView: TextView = view.findViewById(R.id.profile_email)
        val editProfileButton: TextView = view.findViewById(R.id.edit_profile_button)
        val settingsButton: TextView = view.findViewById(R.id.settings_button)
        val logoutButton: Button = view.findViewById(R.id.logout_button)

        // Set the user's name and email
        nameTextView.text = prefsManager.getUserName()
        emailTextView.text = prefsManager.getUserEmail()

        editProfileButton.setOnClickListener {
            Toast.makeText(context, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        }

        settingsButton.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            // Perform logout
            prefsManager.logoutUser()

            // Go back to the Login screen
            val intent = Intent(activity, LoginActivity::class.java)
            // These flags clear the entire app history, so the user can't go back to MainActivity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}