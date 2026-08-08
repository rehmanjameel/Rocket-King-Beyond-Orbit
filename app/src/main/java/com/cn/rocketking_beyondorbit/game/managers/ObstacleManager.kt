package com.cn.rocketking_beyondorbit.game.managers

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import com.cn.rocketking_beyondorbit.game.objects.Asteroid
import com.cn.rocketking_beyondorbit.utils.Constants
import kotlin.random.Random

class ObstacleManager(
    private val context: Context,
    private var screenWidth: Int,
    private var screenHeight: Int
) {

    //==========================================================
    // Obstacle Pair
    //==========================================================

    private data class ObstaclePair(
        val topAsteroids: MutableList<Asteroid>,
        val bottomAsteroids: MutableList<Asteroid>,
        var scored: Boolean = false
    )

    //==========================================================
    // Active Obstacles
    //==========================================================

    private val obstacles =
        mutableListOf<ObstaclePair>()


    //==========================================================
    // Initialization
    //==========================================================

    init {
        reset()
    }


    //==========================================================
    // Reset
    //==========================================================

    @Synchronized
    fun reset() {

        obstacles.clear()

        if (screenWidth <= 0 || screenHeight <= 0) {
            return
        }

        repeat(Constants.INITIAL_OBSTACLE_COUNT) { index ->

            val startX =
                screenWidth +
                        100f +
                        index * Constants.OBSTACLE_DISTANCE

            createObstacle(startX)
        }
    }


    //==========================================================
    // Create Obstacle Pair
    //==========================================================

    private fun createObstacle(startX: Float) {

        if (screenHeight <= 0) {
            return
        }

        val topAsteroids =
            mutableListOf<Asteroid>()

        val bottomAsteroids =
            mutableListOf<Asteroid>()


        //======================================================
        // Safe Area
        //======================================================

        val safeTop =
            Constants.OBSTACLE_MARGIN

        val safeBottom =
            screenHeight -
                    Constants.OBSTACLE_MARGIN

        val gap =
            Constants.OBSTACLE_GAP


        //======================================================
        // Calculate Gap Position
        //======================================================

        val minimumGapCenter =
            safeTop + gap / 2f

        val maximumGapCenter =
            safeBottom - gap / 2f

        // Prevent an invalid random range on smaller screens.
        val gapCenter =
            if (maximumGapCenter > minimumGapCenter) {

                minimumGapCenter +
                        Random.nextFloat() *
                        (maximumGapCenter - minimumGapCenter)

            } else {

                screenHeight / 2f
            }


        //======================================================
        // Top Asteroids
        //======================================================

        var topY =
            safeTop - Constants.ASTEROID_SIZE

        while (
            topY + Constants.ASTEROID_SIZE <
            gapCenter - gap / 2f
        ) {

            topAsteroids.add(
                Asteroid(
                    context = context,
                    startX = startX,
                    startY = topY,
                    rotationSpeed = 0.85f
                )
            )

            topY += Constants.ASTEROID_SIZE
        }


        //======================================================
        // Bottom Asteroids
        //======================================================

        var bottomY =
            gapCenter + gap / 2f

        while (bottomY < safeBottom) {

            bottomAsteroids.add(
                Asteroid(
                    context = context,
                    startX = startX,
                    startY = bottomY,
                    rotationSpeed = -0.7f
                )
            )

            bottomY += Constants.ASTEROID_SIZE
        }


        //======================================================
        // Add Obstacle Pair
        //======================================================

        obstacles.add(
            ObstaclePair(
                topAsteroids = topAsteroids,
                bottomAsteroids = bottomAsteroids
            )
        )
    }


    //==========================================================
    // Update
    //==========================================================

    fun update(
        rocketX: Float,
        onScore: () -> Unit
    ) {

        if (obstacles.isEmpty()) {
            return
        }

        // Keep track of obstacles that need to be removed.
        val obstaclesToRemove =
            mutableListOf<ObstaclePair>()

        // Keep track of how many new obstacles are required.
        var spawnCount = 0


        //======================================================
        // Update Existing Obstacles
        //======================================================

        obstacles.forEach { obstacle ->

            obstacle.topAsteroids.forEach { asteroid ->
                asteroid.update()
            }

            obstacle.bottomAsteroids.forEach { asteroid ->
                asteroid.update()
            }


            //==================================================
            // Score
            //==================================================

            val firstAsteroid =
                obstacle.topAsteroids.firstOrNull()
                    ?: obstacle.bottomAsteroids.firstOrNull()

            if (
                !obstacle.scored &&
                firstAsteroid != null &&
                firstAsteroid.x +
                firstAsteroid.width <
                rocketX
            ) {

                obstacle.scored = true

                onScore()
            }


            //==================================================
            // Check If Completely Off Screen
            //==================================================

            if (
                firstAsteroid != null &&
                firstAsteroid.x +
                firstAsteroid.width <
                -Constants.REMOVE_DISTANCE
            ) {

                obstaclesToRemove.add(obstacle)

                spawnCount++
            }
        }


        //======================================================
        // Remove Old Obstacles
        //======================================================

        obstaclesToRemove.forEach { obstacle ->

            obstacles.remove(obstacle)
        }


        //======================================================
        // Spawn Replacement Obstacles
        //======================================================

        repeat(spawnCount) {

            val lastX =
                getLastObstacleX()

            createObstacle(
                lastX +
                        Constants.OBSTACLE_DISTANCE
            )
        }
    }


    //==========================================================
    // Draw
    //==========================================================

    fun draw(canvas: Canvas) {

        obstacles.forEach { obstacle ->

            obstacle.topAsteroids.forEach { asteroid ->
                asteroid.draw(canvas)
            }

            obstacle.bottomAsteroids.forEach { asteroid ->
                asteroid.draw(canvas)
            }
        }
    }


    //==========================================================
    // Collision Detection
    //==========================================================

    fun checkCollision(
        rocketBounds: RectF
    ): Boolean {

        for (obstacle in obstacles) {

            for (asteroid in obstacle.topAsteroids) {

                if (
                    asteroid.isVisible &&
                    RectF.intersects(
                        rocketBounds,
                        asteroid.bounds
                    )
                ) {

                    return true
                }
            }

            for (asteroid in obstacle.bottomAsteroids) {

                if (
                    asteroid.isVisible &&
                    RectF.intersects(
                        rocketBounds,
                        asteroid.bounds
                    )
                ) {

                    return true
                }
            }
        }

        return false
    }


    //==========================================================
    // Screen Size
    //==========================================================

    @Synchronized
    fun updateScreenSize(
        width: Int,
        height: Int
    ) {

        if (width <= 0 || height <= 0) {
            return
        }

        screenWidth = width
        screenHeight = height

        reset()
    }


    //==========================================================
    // Get Last Obstacle Position
    //==========================================================

    private fun getLastObstacleX(): Float {

        if (obstacles.isEmpty()) {
            return screenWidth.toFloat()
        }

        var lastX =
            screenWidth.toFloat()

        obstacles.forEach { obstacle ->

            val asteroid =
                obstacle.topAsteroids.firstOrNull()
                    ?: obstacle.bottomAsteroids.firstOrNull()

            if (asteroid != null) {

                val rightEdge =
                    asteroid.x + asteroid.width

                if (rightEdge > lastX) {

                    lastX = rightEdge
                }
            }
        }

        return lastX
    }
}