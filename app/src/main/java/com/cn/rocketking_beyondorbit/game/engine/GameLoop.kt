package com.cn.rocketking_beyondorbit.game.engine

import com.cn.rocketking_beyondorbit.utils.Constants

/**
 * Controls the continuous game update/render cycle.
 */
class GameLoop(
    private val engine: GameEngine
) : Thread() {

    @Volatile
    private var running = false


    //==========================================================
    // Start
    //==========================================================

    fun startLoop() {

        if (running) {
            return
        }

        running = true

        start()
    }


    //==========================================================
    // Stop
    //==========================================================

    fun stopLoop() {

        running = false

        interrupt()
    }


    //==========================================================
    // Game Loop
    //==========================================================

    override fun run() {

        while (running) {

            val frameStart = System.currentTimeMillis()

            // Update game logic.
            engine.update()

            // Render current game state.
            engine.render()

            // Calculate remaining frame time.
            val frameTime =
                System.currentTimeMillis() - frameStart

            val sleepTime =
                Constants.FRAME_TIME - frameTime

            if (sleepTime > 0) {

                try {

                    sleep(sleepTime)

                } catch (_: InterruptedException) {

                    // Thread was intentionally stopped.
                    running = false
                }
            }
        }
    }
}