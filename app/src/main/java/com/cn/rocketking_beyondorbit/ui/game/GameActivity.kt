package com.cn.rocketking_beyondorbit.ui.game

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.cn.rocketking_beyondorbit.R
import com.cn.rocketking_beyondorbit.databinding.ActivityGameBinding
import com.cn.rocketking_beyondorbit.ui.pause.PauseDialog

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        WindowCompat.getInsetsController(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = false
        // Tells the window to extend layout bounds into the system bars area
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding =
            ActivityGameBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { view, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }


        //======================================================
        // Initial HUD
        //======================================================

        binding.txtScore.text = "0"

        binding.txtDistance.text = "0 m"


        //======================================================
        // Score Updates
        //======================================================

        binding.gameView.onScoreChanged = { score ->

            runOnUiThread {

                binding.txtScore.text =
                    score.toString()
            }
        }


        //======================================================
        // Distance Updates
        //======================================================

        binding.gameView.onDistanceChanged = { distance ->

            runOnUiThread {

                binding.txtDistance.text =
                    "$distance m"
            }
        }


        //======================================================
        // Pause Button
        //======================================================

        binding.btnPause.setOnClickListener {

            if (
                binding.gameView.getGameState()
                == com.cn.rocketking_beyondorbit.game.engine.GameState.PLAYING
            ) {

                binding.gameView.pauseGame()

                showPauseDialog()
            }
        }
    }


    //==========================================================
    // Pause Dialog
    //==========================================================

    private fun showPauseDialog() {

        PauseDialog(
            this,

            onResume = {

                binding.gameView.resumeGame()
            },

            onRestart = {

                binding.gameView.restartGame()

                binding.txtScore.text = "0"

                binding.txtDistance.text = "0 m"
            },

            onHome = {

                finish()
            }

        ).show()
    }
}