package com.cn.rocketking_beyondorbit.game.engine

import com.cn.rocketking_beyondorbit.game.managers.ObstacleManager
import com.cn.rocketking_beyondorbit.game.objects.Rocket
import com.cn.rocketking_beyondorbit.game.renderer.GameView
import com.cn.rocketking_beyondorbit.utils.Constants
import com.cn.rocketking_beyondorbit.game.sound.GameSoundManager
import com.cn.rocketking_beyondorbit.utils.AppPreferences
import com.cn.rocketking_beyondorbit.utils.GameVibration

class GameEngine(
    private val gameView: GameView,
    screenWidth: Int,
    screenHeight: Int
) {
    var lastScore = 0
        private set
    var distance = 0
        private set
    @Volatile
    private var restartRequested = false
    private val soundManager =
        GameSoundManager(gameView.context)
    private var distanceAccumulator = 0f
    //==========================================================
    // Game Objects
    //==========================================================

    val rocket =
        Rocket(
            context = gameView.context,
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )

    val obstacleManager =
        ObstacleManager(
            context = gameView.context,
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )

    //==========================================================
    // Game State
    //==========================================================

    var gameState =
        GameState.READY
        private set

    //==========================================================
    // Score
    //==========================================================

    var score = 0
        private set

    //==========================================================
    // Update
    //==========================================================

    fun update() {

        if (restartRequested) {

            restartRequested = false

            startGame()
        }

        if (gameState != GameState.PLAYING) {
            return
        }

        //======================================================
        // Rocket
        //======================================================

        rocket.update()

        //======================================================
        // Distance
        //======================================================

        distanceAccumulator += Constants.OBSTACLE_SPEED / 60f

        distance = distanceAccumulator.toInt()

        //======================================================
        // Obstacles + Score
        //======================================================

        obstacleManager.update(
            rocketX = rocket.x,
            onScore = {

                score++

                soundManager.playScore()
            }
        )

        //======================================================
        // Score Update
        //======================================================

        if (score != lastScore) {

            lastScore = score

            gameView.post {

                gameView.onScoreChanged?.invoke(score)
            }
        }

        //======================================================
        // Distance Update
        //======================================================

        gameView.post {

            gameView.onDistanceChanged?.invoke(
                distance
            )
        }

        //======================================================
        // Collision
        //======================================================

        if (
            obstacleManager.checkCollision(
                rocket.bounds
            )
        ) {

            gameOver()

            return
        }

        //======================================================
        // Screen Boundary
        //======================================================

        if (
            rocket.y <= 0f ||
            rocket.y + rocket.height >= gameView.height
        ) {

            gameOver()
        }
    }

    //==========================================================
    // Render
    //==========================================================

    fun render() {

        gameView.drawFrame(this)
    }

    //==========================================================
    // Start Game
    //==========================================================

    fun startGame() {

        score = 0
        lastScore = 0

        distance = 0
        distanceAccumulator = 0f

        rocket.reset()

        obstacleManager.reset()

        gameState = GameState.PLAYING

        gameView.post {

            gameView.onScoreChanged?.invoke(0)

            gameView.onDistanceChanged?.invoke(0)

            gameView.notifyGameStarted()
        }
    }

    fun requestRestart() {

        restartRequested = true
    }
    //==========================================================
    // Pause
    //==========================================================

    fun pauseGame() {

        if (
            gameState ==
            GameState.PLAYING
        ) {

            gameState =
                GameState.PAUSED
        }
    }

    //==========================================================
    // Resume
    //==========================================================

    fun resumeGame() {

        if (
            gameState ==
            GameState.PAUSED
        ) {

            gameState =
                GameState.PLAYING
        }
    }

    //==========================================================
    // Game Over
    //==========================================================

    fun gameOver() {

        if (gameState == GameState.GAME_OVER) {
            return
        }

        saveGameResult()

        gameState = GameState.GAME_OVER

        soundManager.playGameOver()

        GameVibration.vibrate(
            gameView.context,
            150L
        )
    }

    //==========================================================
    // Reset
    //==========================================================

    fun resetGame() {

        score = 0

        rocket.reset()

        obstacleManager.reset()

        gameState =
            GameState.READY
    }

    fun releaseSounds() {

        soundManager.release()
    }

    //==========================================================
    // Touch
    //==========================================================

    fun onTap() {

        when (gameState) {

            GameState.READY -> {

                startGame()

                rocket.thrust()

                soundManager.playRocketThrust()
            }

            GameState.PLAYING -> {

                rocket.thrust()

                soundManager.playRocketThrust()
            }

            GameState.GAME_OVER -> {

                // Do nothing.
                // GameOverDialog handles Play Again / Main Menu.
            }

            GameState.PAUSED -> {

                // PauseDialog handles Resume / Restart / Main Menu.
            }

            GameState.LOADING -> {

                // Game is not ready for input.
            }
        }
    }

    //==========================================================
    // Screen Size
    //==========================================================

    fun updateScreenSize(
        width: Int,
        height: Int
    ) {

        rocket.updateScreenSize(
            width,
            height
        )

        obstacleManager.updateScreenSize(
            width,
            height
        )
    }

    private fun saveGameResult() {

        val bestScore =
            AppPreferences.getInt(
                AppPreferences.KEY_BEST_SCORE
            )

        val bestDistance =
            AppPreferences.getInt(
                AppPreferences.KEY_BEST_DISTANCE
            )

        if (score > bestScore) {

            AppPreferences.saveInt(
                AppPreferences.KEY_BEST_SCORE,
                score
            )
        }

        if (distance > bestDistance) {

            AppPreferences.saveInt(
                AppPreferences.KEY_BEST_DISTANCE,
                distance
            )
        }
    }
}