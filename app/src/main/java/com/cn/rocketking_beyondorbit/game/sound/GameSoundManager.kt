package com.cn.rocketking_beyondorbit.game.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.cn.rocketking_beyondorbit.R

class GameSoundManager(
    context: Context
) {

    //==========================================================
    // Sound Pool
    //==========================================================

    private val soundPool: SoundPool

    //==========================================================
    // Sound IDs
    //==========================================================

    private val rocketThrustSound: Int
    private val scoreSound: Int
    private val collisionSound: Int
    private val buttonClickSound: Int
    private val gameOverSound: Int

    //==========================================================
    // Initialization
    //==========================================================

    init {

        val audioAttributes =
            AudioAttributes.Builder()
                .setUsage(
                    AudioAttributes.USAGE_GAME
                )
                .setContentType(
                    AudioAttributes.CONTENT_TYPE_SONIFICATION
                )
                .build()

        soundPool =
            SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build()

        rocketThrustSound =
            soundPool.load(
                context,
                R.raw.ship_thrust,
                1
            )

        scoreSound =
            soundPool.load(
                context,
                R.raw.score_1,
                1
            )

        collisionSound =
            soundPool.load(
                context,
                R.raw.blast,
                1
            )

        buttonClickSound =
            soundPool.load(
                context,
                R.raw.button_click,
                1
            )

        gameOverSound =
            soundPool.load(
                context,
                R.raw.game_over,
                1
            )
    }

    //==========================================================
    // Rocket Thrust
    //==========================================================

    fun playRocketThrust() {

        soundPool.play(
            rocketThrustSound,
            0.65f,
            0.65f,
            1,
            0,
            1f
        )
    }

    //==========================================================
    // Score
    //==========================================================

    fun playScore() {

        soundPool.play(
            scoreSound,
            0.8f,
            0.8f,
            1,
            0,
            1f
        )
    }

    //==========================================================
    // Collision
    //==========================================================

    fun playCollision() {

        soundPool.play(
            collisionSound,
            1f,
            1f,
            1,
            0,
            1f
        )
    }

    //==========================================================
    // Button Click
    //==========================================================

    fun playButtonClick() {

        soundPool.play(
            buttonClickSound,
            0.7f,
            0.7f,
            1,
            0,
            1f
        )
    }

    //==========================================================
    // Game Over
    //==========================================================

    fun playGameOver() {

        soundPool.play(
            gameOverSound,
            0.9f,
            0.9f,
            1,
            0,
            1f
        )
    }

    //==========================================================
    // Release
    //==========================================================

    fun release() {

        soundPool.release()
    }
}