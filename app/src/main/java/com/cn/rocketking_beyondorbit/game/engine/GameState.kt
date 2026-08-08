package com.cn.rocketking_beyondorbit.game.engine

/**
 * Represents the current state of the game.
 */
enum class GameState {

    // Initial state before the game starts.
    READY,

    // Game is actively running.
    PLAYING,

    // Game is temporarily stopped.
    PAUSED,

    // Rocket has collided with an obstacle.
    GAME_OVER,

    LOADING

}