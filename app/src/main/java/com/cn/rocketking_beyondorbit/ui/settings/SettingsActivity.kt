package com.cn.rocketking_beyondorbit.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cn.rocketking_beyondorbit.R
import com.cn.rocketking_beyondorbit.databinding.ActivitySettingsBinding
import com.cn.rocketking_beyondorbit.utils.AppPreferences

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootSettings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //======================================================
        // Load Settings
        //======================================================

        loadSettings()


        //======================================================
        // Back
        //======================================================
        binding.btnBack.setOnClickListener {

            finish()

        }

        //======================================================
        // Sound
        //======================================================

        binding.switchSound.setOnCheckedChangeListener { _, enabled ->

            AppPreferences.saveBoolean(
                AppPreferences.KEY_SOUND,
                enabled
            )
        }

        //======================================================
        // Music
        //======================================================

        binding.switchMusic.setOnCheckedChangeListener { _, enabled ->

            AppPreferences.saveBoolean(
                AppPreferences.KEY_MUSIC,
                enabled
            )
        }


        //======================================================
        // Vibration
        //======================================================

        binding.switchVibration.setOnCheckedChangeListener { _, enabled ->

            AppPreferences.saveBoolean(
                AppPreferences.KEY_VIBRATION,
                enabled
            )
        }


        //======================================================
        // Reset Best Score
        //======================================================

        binding.btnResetScore.setOnClickListener {

            showResetScoreDialog()
        }


        //======================================================
        // About
        //======================================================

        binding.btnAbout.setOnClickListener {

            showAboutDialog()
        }


        //======================================================
        // Privacy Policy
        //======================================================

        binding.btnPrivacy.setOnClickListener {

            openPrivacyPolicy()
        }

    }

    //==========================================================
    // Load Settings
    //==========================================================

    private fun loadSettings() {

        binding.switchSound.isChecked =
            AppPreferences.getBoolean(
                AppPreferences.KEY_SOUND,
                true
            )

        binding.switchMusic.isChecked =
            AppPreferences.getBoolean(
                AppPreferences.KEY_MUSIC,
                true
            )

        binding.switchVibration.isChecked =
            AppPreferences.getBoolean(
                AppPreferences.KEY_VIBRATION,
                true
            )

        binding.txtVersion.text =
            "Version 1.0"
    }


    //==========================================================
    // Reset Score Dialog
    //==========================================================

    private fun showResetScoreDialog() {

        AlertDialog.Builder(this)
            .setTitle("Reset Best Results?")
            .setMessage(
                "Your best score and best distance will be permanently deleted."
            )
            .setNegativeButton(
                "Cancel",
                null
            )
            .setPositiveButton(
                "Reset"
            ) { _, _ ->

                AppPreferences.resetBestResults()
            }
            .show()
    }


    //==========================================================
    // About Dialog
    //==========================================================

    private fun showAboutDialog() {

        AlertDialog.Builder(this)
            .setTitle("Rocket King: Beyond Orbit")
            .setMessage(
                "Fly your rocket through the asteroid field, " +
                        "avoid collisions, and beat your best score.\n\n" +
                        "Version 1.0"
            )
            .setPositiveButton(
                "OK",
                null
            )
            .show()
    }


    //==========================================================
    // Privacy Policy
    //==========================================================

    private fun openPrivacyPolicy() {

        /*
         * Replace this with your final published privacy-policy
         * URL before releasing the game.
         */
        val privacyUrl =
            "https://YOUR-PRIVACY-POLICY-URL"

        val intent =
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(privacyUrl)
            )

        startActivity(intent)
    }
}