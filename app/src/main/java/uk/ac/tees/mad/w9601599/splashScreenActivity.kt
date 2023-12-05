package uk.ac.tees.mad.w9601599

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView

class splashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val img1 = findViewById<ImageView>(R.id.img1)
        val img2 = findViewById<ImageView>(R.id.img2)
        val img3 = findViewById<ImageView>(R.id.img3)
        val img4 = findViewById<ImageView>(R.id.imageView6)
        val img5 = findViewById<ImageView>(R.id.imageView5)
        val img6 = findViewById<ImageView>(R.id.imageView2)
        val logo = findViewById<ImageView>(R.id.logoHealth)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        img1.startAnimation(scaleUp)
        img2.startAnimation(scaleUp)
        img3.startAnimation(scaleUp)
        img4.startAnimation(scaleUp)
        img5.startAnimation(scaleUp)
        img6.startAnimation(scaleUp)

        logo.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}