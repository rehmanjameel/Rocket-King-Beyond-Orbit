package com.cn.rocketking_beyondorbit.ui.pause

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.cn.rocketking_beyondorbit.R

class PauseDialog(
    context: Context,
    private val onResume: () -> Unit,
    private val onRestart: () -> Unit,
    private val onHome: () -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //==========================================================
        // Remove default dialog title
        //==========================================================

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        //==========================================================
        // Set Layout
        //==========================================================

        setContentView(R.layout.dialog_pause)

        //==========================================================
        // Dialog Window
        //==========================================================

        window?.apply {

            setBackgroundDrawableResource(
                android.R.color.transparent
            )

            setDimAmount(0.65f)

            addFlags(
                WindowManager.LayoutParams.FLAG_DIM_BEHIND
            )

            setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        //==========================================================
        // Buttons
        //==========================================================

        val btnResume =
            findViewById<Button>(R.id.btnResume)

        val btnRestart =
            findViewById<Button>(R.id.btnRestart)

        val btnHome =
            findViewById<Button>(R.id.btnHome)

        //==========================================================
        // Resume
        //==========================================================

        btnResume.setOnClickListener {

            dismiss()

            onResume()
        }

        //==========================================================
        // Restart
        //==========================================================

        btnRestart.setOnClickListener {

            dismiss()

            onRestart()
        }

        //==========================================================
        // Main Menu
        //==========================================================

        btnHome.setOnClickListener {

            dismiss()

            onHome()
        }

        //==========================================================
        // Prevent accidental dismissal
        //==========================================================

        setCanceledOnTouchOutside(false)

        setCancelable(false)
    }

    //==========================================================
    // Show Dialog
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