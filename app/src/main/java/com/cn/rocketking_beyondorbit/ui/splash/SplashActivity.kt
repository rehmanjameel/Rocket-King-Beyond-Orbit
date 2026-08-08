package com.cn.rocketking_beyondorbit.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cn.rocketking_beyondorbit.R
import com.cn.rocketking_beyondorbit.databinding.ActivitySplashBinding
import com.cn.rocketking_beyondorbit.ui.splash.menu.MainMenuActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.root.postDelayed({

            startActivity(
                Intent(
                    this,
                    MainMenuActivity::class.java
                )
            )

            finish()

        }, 2000)
    }
}