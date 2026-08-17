package com.cn.rocketking_beyondorbit.utils

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission

object GameVibration {

    private fun getVibrator(
        context: Context
    ): Vibrator {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            val manager =
                context.getSystemService(
                    Context.VIBRATOR_MANAGER_SERVICE
                ) as VibratorManager

            manager.defaultVibrator

        } else {

            @Suppress("DEPRECATION")
            context.getSystemService(
                Context.VIBRATOR_SERVICE
            ) as Vibrator
        }
    }


    @RequiresPermission(Manifest.permission.VIBRATE)
    fun vibrate(
        context: Context,
        duration: Long
    ) {

        if (
            !AppPreferences.getBoolean(
                AppPreferences.KEY_VIBRATION,
                true
            )
        ) {
            return
        }

        val vibrator =
            getVibrator(context)

        if (!vibrator.hasVibrator()) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )

        } else {

            @Suppress("DEPRECATION")
            vibrator.vibrate(duration)
        }
    }
}