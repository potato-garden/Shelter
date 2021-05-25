package com.bangkit.shelter.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.databinding.ActivitySplashScreenBinding
import com.bangkit.shelter.ui.home.ui.HomeActivity
import com.bangkit.shelter.ui.auth.MainActivity
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth


    companion object {
        const val delay = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(delay)
                    if (user != null) {
                        startActivity(Intent(baseContext, HomeActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(baseContext, MainActivity::class.java))
                        finish()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}