package com.example.datastoreexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class SplashActivity : AppCompatActivity() {

    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lottieAnimationView = findViewById(R.id.lottie_animation)

        lottieAnimationView.setAnimation(R.raw.hearth)
        lottieAnimationView.addAnimatorUpdateListener { animation ->
            if (animation.isRunning) {
            } else {
                lottieAnimationView.postDelayed({
                    navigateToMainScreen()
                }, 2000)
            }
        }
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
