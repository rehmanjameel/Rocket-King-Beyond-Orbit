package com.cn.rocketking_beyondorbit.game.objects

import android.graphics.Canvas
import android.graphics.RectF

/**
 * Base class for every object inside the game world.
 */
abstract class GameObject {

    //==========================================================
    // Position
    //==========================================================

    var x = 0f

    var y = 0f


    //==========================================================
    // Size
    //==========================================================

    var width = 0f

    var height = 0f


    //==========================================================
    // Visibility
    //==========================================================

    var isVisible = true


    //==========================================================
    // Collision
    //==========================================================

    val bounds = RectF()


    //==========================================================
    // Update
    //==========================================================

    abstract fun update()


    //==========================================================
    // Draw
    //==========================================================

    abstract fun draw(canvas: Canvas)


    //==========================================================
    // Update Collision Bounds
    //==========================================================

    open fun updateBounds() {

        bounds.set(
            x,
            y,
            x + width,
            y + height
        )
    }


    //==========================================================
    // Collision Check
    //==========================================================

    fun intersects(other: GameObject): Boolean {

        return isVisible &&
                other.isVisible &&
                RectF.intersects(
                    bounds,
                    other.bounds
                )
    }
}