package com.cn.rocketking_beyondorbit.ui.gameover


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.cn.rocketking_beyondorbit.R

class GameOverDialog(
    context: Context,
    private val score: Int,
    private val bestScore: Int,
    private val distance: Int,
    private val onPlayAgain: () -> Unit,
    private val onMainMenu: () -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //======================================================
        // Remove Default Dialog Title
        //======================================================

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        //======================================================
        // Layout
        //======================================================

        setContentView(R.layout.dialog_game_over)

        //======================================================
        // Window
        //======================================================

        window?.apply {

            setBackgroundDrawableResource(
                android.R.color.transparent
            )

            setDimAmount(0.70f)

            addFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND
            )
        }

        //======================================================
        // TextViews
        //======================================================

        val txtScore =
            findViewById<TextView>(R.id.txtScore)

        val txtBestScore =
            findViewById<TextView>(R.id.txtBestScore)

        val txtDistance =
            findViewById<TextView>(R.id.txtDistance)

        //======================================================
        // Values
        //======================================================

        txtScore.text = score.toString()

        txtBestScore.text = bestScore.toString()

        txtDistance.text = "$distance m"

        //======================================================
        // Buttons
        //======================================================

        val btnPlayAgain =
            findViewById<MaterialButton>(
                R.id.btnPlayAgain
            )

        val btnMainMenu =
            findViewById<MaterialButton>(
                R.id.btnMainMenu
            )

        //======================================================
        // Play Again
        //======================================================

        btnPlayAgain.setOnClickListener {

            dismiss()

            onPlayAgain()
        }

        //======================================================
        // Main Menu
        //======================================================

        btnMainMenu.setOnClickListener {

            dismiss()

            onMainMenu()
        }

        //======================================================
        // Prevent Outside Dismiss
        //======================================================

        setCanceledOnTouchOutside(false)

        setCancelable(false)
    }

    //==========================================================
    // Show
    //==========================================================

    override fun show() {

        super.show()

        window?.setLayout(
            dpToPx(420),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    //==========================================================
    // DP To PX
    //==========================================================

    private fun dpToPx(dp: Int): Int {

        return (
                dp *
                        context.resources.displayMetrics.density
                ).toInt()
    }
}