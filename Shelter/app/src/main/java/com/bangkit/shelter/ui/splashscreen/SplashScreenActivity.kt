package com.bangkit.shelter.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bangkit.shelter.MainActivity
import com.bangkit.shelter.databinding.ActivitySplashScreenBinding
import com.bangkit.shelter.ui.auth.MainAuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import org.tensorflow.lite.Interpreter

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
        supportActionBar?.hide()

        val conditions = CustomModelDownloadConditions.Builder()
            .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
            .build()
        FirebaseModelDownloader.getInstance()
            .getModel("personality_detection", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
                conditions)
            .addOnSuccessListener { model: CustomModel? ->
                // Download complete. Depending on your app, you could enable the ML
                // feature, or switch from the local model to the remote model, etc.

                // The CustomModel object contains the local path of the model file,
                // which you can use to instantiate a TensorFlow Lite interpreter.
                val modelFile = model?.file
                if (modelFile != null) {
                    val interpreter = Interpreter(modelFile)
                }
            }

        val sharedPref = applicationContext?.getSharedPreferences("night", Context.MODE_PRIVATE)
        val booleanValue: Boolean = sharedPref!!.getBoolean("night_mode", true)
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser


        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(delay)
                    if (user != null) {
                        startActivity(Intent(baseContext, MainActivity::class.java))
                        finish()
                    } else {
                        startActivity(Intent(baseContext, MainAuthActivity::class.java))
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