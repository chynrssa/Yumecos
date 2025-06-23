package com.example.yumecos

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.yumecos.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Panggil installSplashScreen() SEBELUM super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animasi kustom Anda tetap bisa dijalankan setelahnya
        val slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom)
        binding.ivLogoSplash.startAnimation(slideInAnimation)

        // Pindah ke MainActivity setelah delay
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // Durasi bisa dipersingkat karena sudah ada splash screen sistem
    }
}