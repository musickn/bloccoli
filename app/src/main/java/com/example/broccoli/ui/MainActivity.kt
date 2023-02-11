package com.example.broccoli.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
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
    private lateinit var preferences: SharedPreferences
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dialogBinding = RequestInviteDialogBinding.inflate(layoutInflater)
        preferences = this.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        setContentView(binding.root)

//      Check if the user has already requested an invitation
        if (preferences.getBoolean("invited", false)) {
            displayInvitedScreen()
        } else {
            displayRequestScreen()
        }
    }

    private fun displayRequestScreen() {
        binding.txtSubtitle.text = getString(R.string.request_invite_subtitle)
        with(binding.btnRequestInvite) {
            text = getString(R.string.request_invite)
            setOnClickListener { showRequestInviteDialog() }
        }
    }

    private fun displayInvitedScreen() {
        binding.txtSubtitle.text = getString(R.string.already_invited)
        with(binding.btnRequestInvite) {
            text = getString(R.string.cancel_invite)
            setOnClickListener { showCancelInviteDialog() }
        }
    }

    private fun showRequestInviteDialog() {
        dialogBinding = RequestInviteDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnSend.setOnClickListener {
            val fullName = dialogBinding.fullName.text.toString().trim()
            val email = dialogBinding.email.text.toString().trim()
            val confirmEmail = dialogBinding.confirmEmail.text.toString().trim()

            var isValid = true
            if (fullName.length < 3) {
                dialogBinding.fullName.error = getString(R.string.error_minimum_characters)
                isValid = false
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                dialogBinding.email.error = getString(R.string.error_invalid_email)
                isValid = false
            }
            if (email != confirmEmail) {
                dialogBinding.confirmEmail.error = getString(R.string.error_email_mismatch)
                isValid = false
            }

            if (isValid) {
                viewModel.requestInvitation(fullName, email)

                // Show a "congratulations" message
                // ...

                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showCancelInviteDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.cancel_invite)
            .setMessage(R.string.cancel_invite_confirm)
            .setPositiveButton(R.string.confirm) { _, _ ->
                // Clear shared preferences when user confirms
                preferences.edit().clear().apply()

                binding.txtSubtitle.text = getString(R.string.request_invite_subtitle)
                with(binding.btnRequestInvite) {
                    text = getString(R.string.request_invite)
                    setOnClickListener { showRequestInviteDialog() }
                }
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
        dialog.show()
    }
}
