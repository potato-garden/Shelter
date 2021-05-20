package com.bangkit.shelter.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.shelter.R
import com.bangkit.shelter.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)

        with(activitySignUpBinding){
            btnBack.setOnClickListener{
                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
            }
        }
    }
}