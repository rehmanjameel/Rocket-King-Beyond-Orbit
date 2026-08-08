package com.cn.rocketking_beyondorbit.utils

object Constants {

    //==========================================================
    // Game Loop
    //==========================================================

    const val TARGET_FPS = 60

    const val FRAME_TIME = 1000L / TARGET_FPS


    //==========================================================
    // Screen
    //==========================================================

    var SCREEN_WIDTH = 0

    var SCREEN_HEIGHT = 0


    //==========================================================
    // Rocket Physics
    //==========================================================

    // Downward acceleration.
    const val GRAVITY = 0.50f

    // Upward force applied when the player taps.
    const val THRUST = -9f

    // Maximum downward velocity.
    const val MAX_FALL_SPEED = 12f

    // Maximum upward velocity.
    const val MAX_RISE_SPEED = -9.5f


    //==========================================================
    // Rocket
    //==========================================================

    const val ROCKET_WIDTH = 120f

    const val ROCKET_HEIGHT = 60f

    // Rocket's horizontal position.
    // The rocket stays around this percentage of screen width.
    const val ROCKET_START_X_RATIO = 0.20f


    //==========================================================
    // Obstacles
    //==========================================================

    // Horizontal movement speed of asteroids.
    const val OBSTACLE_SPEED = 15f

    // Distance between two obstacle pairs.
    const val OBSTACLE_DISTANCE = 300f

    // Gap between top and bottom asteroid groups.
    const val OBSTACLE_GAP = 200f

    // Minimum distance from top/bottom screen edges.
    const val OBSTACLE_MARGIN = 80f

    // Size of each asteroid.
    const val ASTEROID_SIZE = 100f

    // Number of obstacle pairs initially created.
    const val INITIAL_OBSTACLE_COUNT = 3

    // How far behind the screen an asteroid can travel
    // before being removed.
    const val REMOVE_DISTANCE = 200f


    //==========================================================
    // Scoring
    //==========================================================

    const val SCORE_PER_OBSTACLE = 1

}