package com.bangkit.shelter.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.shelter.databinding.ActivityMainAuthBinding

class MainAuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        with(binding){
            btnLogin.setOnClickListener{
                startActivity(Intent(baseContext, LoginActivity::class.java))
            }
            btnSignin.setOnClickListener {
                startActivity(Intent(baseContext, RegisterActivity::class.java))
            }
        }
    }
}