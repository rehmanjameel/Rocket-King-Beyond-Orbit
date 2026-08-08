package com.cn.rocketking_beyondorbit.game.background


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.cn.rocketking_beyondorbit.R

class Background(
    context: Context
) {

    //==========================================================
    // Background Bitmap
    //==========================================================

    private val bitmap: Bitmap =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.background
        )


    //==========================================================
    // Paint
    //==========================================================

    private val paint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            isFilterBitmap = true

            isDither = true
        }


    //==========================================================
    // Source Rectangle
    //==========================================================

    private val sourceRect =
        Rect()


    //==========================================================
    // Draw
    //==========================================================

    fun draw(
        canvas: Canvas,
        width: Int,
        height: Int
    ) {

        if (width <= 0 || height <= 0) {
            return
        }

        /*
         * Crop the source image so the aspect ratio remains
         * suitable for the landscape game screen.
         */

        val sourceWidth =
            bitmap.width

        val sourceHeight =
            bitmap.height

        val sourceRatio =
            sourceWidth.toFloat() /
                    sourceHeight.toFloat()

        val screenRatio =
            width.toFloat() /
                    height.toFloat()

        if (sourceRatio > screenRatio) {

            // Image is wider than the screen.
            val cropWidth =
                (sourceHeight * screenRatio)
                    .toInt()

            val left =
                (sourceWidth - cropWidth) / 2

            sourceRect.set(
                left,
                0,
                left + cropWidth,
                sourceHeight
            )

        } else {

            // Image is taller than the screen.
            val cropHeight =
                (sourceWidth / screenRatio)
                    .toInt()

            val top =
                (sourceHeight - cropHeight) / 2

            sourceRect.set(
                0,
                top,
                sourceWidth,
                top + cropHeight
            )
        }

        canvas.drawBitmap(
            bitmap,
            sourceRect,
            Rect(
                0,
                0,
                width,
                height
            ),
            paint
        )
    }
}