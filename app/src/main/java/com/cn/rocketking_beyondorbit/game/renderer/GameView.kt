package com.cn.rocketking_beyondorbit.game.renderer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.res.ResourcesCompat
import com.cn.rocketking_beyondorbit.R
import com.cn.rocketking_beyondorbit.game.background.Background
import com.cn.rocketking_beyondorbit.game.engine.GameEngine
import com.cn.rocketking_beyondorbit.game.engine.GameLoop
import com.cn.rocketking_beyondorbit.game.engine.GameState
import com.cn.rocketking_beyondorbit.utils.Constants

class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {
    private var gameOverDialogRequested = false
    //==========================================================
    // Game Engine
    //==========================================================

    private lateinit var gameEngine: GameEngine
    //==========================================================
    // Game Loop
    //==========================================================

    private lateinit var gameLoop: GameLoop

    //==========================================================
    // Background
    //==========================================================

    private val background = Background(context)

    //==========================================================
    // Game Over Paint
    //==========================================================

    private val gameOverPaint =
        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            color = Color.WHITE

            textAlign = Paint.Align.CENTER

            typeface = ResourcesCompat.getFont(
                context,
                R.font.orbitron
            )

            setShadowLayer(
                6f,
                2f,
                2f,
                Color.BLACK
            )
        }

    //==========================================================
    // Callbacks
    //==========================================================

    var onPauseRequested: (() -> Unit)? = null
    var onGameStarted: (() -> Unit)? = null
    var onScoreChanged: ((Int) -> Unit)? = null

    var onDistanceChanged: ((Int) -> Unit)? = null
    var onGameOverRequested: ((Int, Int, Int) -> Unit)? = null
    //==========================================================
    // Initialization
    //==========================================================

    init {

        holder.addCallback(this)

        isFocusable = true

        keepScreenOn = true
    }

    //==========================================================
    // Surface Created
    //==========================================================

    override fun surfaceCreated(holder: SurfaceHolder) {

        val surfaceWidth = width.coerceAtLeast(1)

        val surfaceHeight = height.coerceAtLeast(1)

        Constants.SCREEN_WIDTH = surfaceWidth
        Constants.SCREEN_HEIGHT = surfaceHeight

        gameEngine = GameEngine(
            gameView = this,
            screenWidth = surfaceWidth,
            screenHeight = surfaceHeight
        )

        gameLoop = GameLoop(gameEngine)

        // Game starts in READY state.
        // First touch starts the game.
        gameLoop.startLoop()
    }

    //==========================================================
    // Surface Changed
    //==========================================================

    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int
    ) {

        if (width <= 0 || height <= 0) {
            return
        }

        Constants.SCREEN_WIDTH = width
        Constants.SCREEN_HEIGHT = height

        if (::gameEngine.isInitialized) {

            gameEngine.updateScreenSize(
                width,
                height
            )
        }
    }

    //==========================================================
    // Surface Destroyed
    //==========================================================

    override fun surfaceDestroyed(holder: SurfaceHolder) {

        if (::gameLoop.isInitialized) {

            gameLoop.stopLoop()
        }

        if (::gameEngine.isInitialized) {

            gameEngine.releaseSounds()
        }
    }

    //==========================================================
    // Draw Frame
    //==========================================================

    fun drawFrame(engine: GameEngine) {

        val canvas =
            try {
                holder.lockCanvas()
            } catch (_: Exception) {
                null
            } ?: return

        try {

            //==================================================
            // Space Background
            //==================================================

            background.draw(
                canvas,
                width,
                height
            )

            //==================================================
            // Asteroids
            //==================================================

            engine.obstacleManager.draw(canvas)

            //==================================================
            // Rocket
            //==================================================

            engine.rocket.draw(canvas)

            //==================================================
            // Ready Message
            //==================================================

            if (engine.gameState == GameState.READY) {

                drawReadyMessage(canvas)
            }

            //==================================================
            // Game Over
            //==================================================

            if (
                engine.gameState == GameState.GAME_OVER &&
                !gameOverDialogRequested
            ) {

                gameOverDialogRequested = true

                val bestScore =
                    com.cn.rocketking_beyondorbit.utils.AppPreferences
                        .getInt(
                            com.cn.rocketking_beyondorbit.utils.AppPreferences.KEY_BEST_SCORE
                        )

                post {

                    onGameOverRequested?.invoke(
                        engine.score,
                        bestScore,
                        engine.distance
                    )
                }
            }

        } finally {

            try {

                holder.unlockCanvasAndPost(canvas)

            } catch (_: Exception) {
                // Surface was destroyed.
            }
        }
    }

    //==========================================================
    // Ready Message
    //==========================================================

    private fun drawReadyMessage(canvas: Canvas) {

        gameOverPaint.textSize = 48f

        canvas.drawText(
            "TAP TO START",
            width / 2f,
            height * 0.70f,
            gameOverPaint
        )
    }

    //==========================================================
    // Game Over Message
    //==========================================================

    private fun drawGameOverMessage(canvas: Canvas) {

        gameOverPaint.textSize = 58f

        canvas.drawText(
            "GAME OVER",
            width / 2f,
            height * 0.40f,
            gameOverPaint
        )

        gameOverPaint.textSize = 34f

        canvas.drawText(
            "TAP TO PLAY AGAIN",
            width / 2f,
            height * 0.58f,
            gameOverPaint
        )
    }

    //==========================================================
    // Touch Events
    //==========================================================

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        if (event.actionMasked != MotionEvent.ACTION_DOWN) {
            return true
        }

        if (!::gameEngine.isInitialized) {
            return true
        }

        gameEngine.onTap()

        performClick()

        return true
    }

    //==========================================================
    // Accessibility Click
    //==========================================================

    override fun performClick(): Boolean {

        super.performClick()

        return true
    }

    //==========================================================
    // Pause
    //==========================================================

    fun pauseGame() {

        if (::gameEngine.isInitialized) {

            gameEngine.pauseGame()
        }
    }

    //==========================================================
    // Resume
    //==========================================================

    fun resumeGame() {

        if (::gameEngine.isInitialized) {

            gameEngine.resumeGame()
        }
    }

    //==========================================================
    // Restart
    //==========================================================

    fun restartGame() {

        if (::gameEngine.isInitialized) {

            gameOverDialogRequested = true

            gameEngine.requestRestart()
        }
    }
    fun notifyGameStarted() {

        gameOverDialogRequested = false
    }
    //==========================================================
    // Score
    //==========================================================

    fun getCurrentScore(): Int {

        return if (::gameEngine.isInitialized) {
            gameEngine.score
        } else {
            0
        }
    }

    //==========================================================
    // Game State
    //==========================================================

    fun getGameState(): GameState {

        return if (::gameEngine.isInitialized) {
            gameEngine.gameState
        } else {
            GameState.LOADING
        }
    }
}