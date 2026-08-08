package com.cn.rocketking_beyondorbit.game.objects

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import com.cn.rocketking_beyondorbit.R
import com.cn.rocketking_beyondorbit.utils.Constants

class Rocket(
    context: Context,
    private var screenWidth: Int,
    private var screenHeight: Int
) : GameObject() {

    //==========================================================
    // Physics
    //==========================================================

    private var velocity = 0f


    //==========================================================
    // Rotation
    //==========================================================

    var rotation = 0f
        private set


    //==========================================================
    // Bitmap
    //==========================================================

    private val bitmap: Bitmap =
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.spaceships
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
    // Initialization
    //==========================================================

    init {

        width = Constants.ROCKET_WIDTH

        height = Constants.ROCKET_HEIGHT

        reset()

    }


    //==========================================================
    // Reset Rocket
    //==========================================================

    fun reset() {

        velocity = 0f

        rotation = 0f

        x =
            screenWidth *
                    Constants.ROCKET_START_X_RATIO

        y =
            (screenHeight - height) / 2f

        isVisible = true

        updateBounds()
    }


    //==========================================================
    // Update Screen Size
    //==========================================================

    fun updateScreenSize(
        width: Int,
        height: Int
    ) {

        screenWidth = width

        screenHeight = height

        x =
            screenWidth *
                    Constants.ROCKET_START_X_RATIO

        if (y + this.height > screenHeight) {

            y = screenHeight - this.height
        }

        updateBounds()
    }


    //==========================================================
    // Update Physics
    //==========================================================

    override fun update() {

        // Apply gravity.
        velocity += Constants.GRAVITY

        // Keep velocity within limits.
        velocity = velocity.coerceIn(
            Constants.MAX_RISE_SPEED,
            Constants.MAX_FALL_SPEED
        )

        // Move vertically.
        y += velocity


        //======================================================
        // Screen Boundaries
        //======================================================

        if (y < 0f) {

            y = 0f

            velocity = 0f
        }

        if (y + height > screenHeight) {

            y = screenHeight - height

            velocity = 0f
        }


        //======================================================
        // Rotation
        //======================================================

        rotation =
            (velocity * 3f)
                .coerceIn(-35f, 60f)


        //======================================================
        // Collision Bounds
        //======================================================

        updateBounds()
    }


    //==========================================================
    // Thrust
    //==========================================================

    fun thrust() {

        velocity += Constants.THRUST

        velocity =
            velocity.coerceAtLeast(
                Constants.MAX_RISE_SPEED
            )

        rotation = -30f
    }


    //==========================================================
    // Draw Rocket
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

        bounds.set(
            x + 8f,
            y + 8f,
            x + width - 8f,
            y + height - 8f
        )
    }
}