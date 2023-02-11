package com.example.broccoli.ui

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.broccoli.R
import com.example.broccoli.databinding.ActivityMainBinding
import com.example.broccoli.databinding.RequestInviteDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogBinding: RequestInviteDialogBinding
//    private val preferencesFileName = "userPrefs"
//    private val preferences = getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dialogBinding = RequestInviteDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if the user has already requested an invitation
//        if (preferences.getBoolean("invited", false)) {
//            displayInvitedScreen()
//        } else {
            displayRequestScreen()
//        }
    }
    private fun displayRequestScreen() {
//        text_header.text = getString(R.string.request_invite_header)
//        text_body.text = getString(R.string.request_invite_body)
//        button_request_invite.text = getString(R.string.request_invite_button)

        binding.btnRequestInvite.setOnClickListener { showRequestInviteDialog() }
    }
    private fun displayInvitedScreen() {
//        text_header.text = getString(R.string.already_registered_header)
//        text_body.text = getString(R.string.already_registered_body)
//        button_request_invite.text = getString(R.string.cancel_invite_button)
//
//        button_request_invite.setOnClickListener {
//            showCancelInviteDialog()
//        }
    }

    private fun showRequestInviteDialog() {
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()

        dialogBinding.btnSend.setOnClickListener {
            val fullName = dialogBinding.fullName.text.toString().trim()
            val email = dialogBinding.email.text.toString().trim()
            val confirmEmail = dialogBinding.confirmEmail.text.toString().trim()

            var isValid = true
            if (fullName.length < 3) {
                dialogBinding.fullName.error = getString(R.string.minimum_characters)
                isValid = false
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                dialogBinding.email.error = getString(R.string.invalid_email)
                isValid = false
            }
            if (email != confirmEmail) {
                dialogBinding.confirmEmail.error = getString(R.string.email_mismatch)
                isValid = false
            }

            if (isValid) {
                // Send the request invite
                // ...

                // Show a "congratulations" message
                // ...

                dialog.dismiss()
            }
        }
        dialog.show()
    }
}
