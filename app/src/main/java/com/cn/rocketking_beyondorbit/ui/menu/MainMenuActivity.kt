package com.cn.rocketking_beyondorbit.ui.splash.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cn.rocketking_beyondorbit.R
import com.cn.rocketking_beyondorbit.databinding.ActivityMainMenuBinding
import com.cn.rocketking_beyondorbit.ui.game.GameActivity
import com.cn.rocketking_beyondorbit.ui.settings.SettingsActivity
import kotlin.jvm.java

class MainMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainMenuBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnPlay.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    GameActivity::class.java
                )
            )

        }

        binding.btnSettings.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                )
            )

        }

        binding.btnExit.setOnClickListener {

            finish()

        }
    }
}