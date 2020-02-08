package com.example.timefighter

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    internal var score: Int = 0

    internal lateinit var tapMeButton: Button
    internal lateinit var yourScoreText: TextView
    internal lateinit var timeView: TextView

    internal var gameStarted = false
    internal var abc = true
    internal lateinit var countDownTimer: CountDownTimer

    //in milliseconds
    internal val initialCountDown: Long = 60000
    internal val countDownInterval: Long = 1000
    internal val timeLeftOnTimer: Long = 60000


    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // log
        Log.d(TAG, "onCreate called. Score is: $score")

        tapMeButton = findViewById(R.id.tapMeButton)
        yourScoreText = findViewById(R.id.yourScoreText)
        timeView = findViewById(R.id.timeView)

        tapMeButton.setOnClickListener { view ->
            incrementScore()

        }

resetGame()
            }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time Left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called.")
    }

    private fun resetGame() {
        score = 0

        yourScoreText.text = getString(R.string.yourScore, score)

        val initialTimeLeft = initialCountDown / 1000
        timeView.text = getString(R.string.timeLeft, initialTimeLeft)

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                timeView.text = getString(R.string.timeLeft, timeLeft)
            }

            override fun onFinish() {
endGame()
            }
        }
gameStarted = false
    }
    private fun incrementScore() {

        if(!gameStarted) {
            countDownTimer.start()
            gameStarted = true
        }
        score += 1
        val newScore = getString(R.string.yourScore, score)
        yourScoreText.text = newScore
    }

    private fun endGame() {
        Toast.makeText( this, getString(R.string.gameOverMessage, score), Toast.LENGTH_LONG).show()
        resetGame()
    }
}
