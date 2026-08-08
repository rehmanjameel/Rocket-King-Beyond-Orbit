package com.cn.rocketking_beyondorbit.game.objects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.cn.rocketking_beyondorbit.R
import com.cn.rocketking_beyondorbit.utils.Constants

class Asteroid(
    context: Context,
    startX: Float,
    startY: Float,
    private val rotationSpeed: Float
) : GameObject() {

    //==========================================================
    // Bitmap
    //==========================================================

    private val bitmap: Bitmap =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.asteroid
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
    // Rotation
    //==========================================================

    private var rotation = 0f


    //==========================================================
    // Initialization
    //==========================================================

    init {

        width = Constants.ASTEROID_SIZE

        height = Constants.ASTEROID_SIZE

        x = startX

        y = startY

        updateBounds()
    }


    //==========================================================
    // Update
    //==========================================================

    override fun update() {

        x -= Constants.OBSTACLE_SPEED

        rotation += rotationSpeed

        if (rotation >= 360f) {
            rotation -= 360f
        }

        updateBounds()
    }


    //==========================================================
    // Draw
    //==========================================================

    override fun draw(canvas: Canvas) {

        if (!isVisible) {
            return
        }

        canvas.save()

        canvas.rotate(
            rotation,
            x + width / 2f,
            y + height / 2f
        )

        canvas.drawBitmap(
            bitmap,
            null,
            bounds,
            paint
        )

        canvas.restore()
    }


    //==========================================================
    // Collision Bounds
    //==========================================================

    override fun updateBounds() {

        // Slightly smaller collision area than the visual asteroid.
        val padding = 10f

        bounds.set(
            x + padding,
            y + padding,
            x + width - padding,
            y + height - padding
        )
    }
}