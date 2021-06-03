package com.bangkit.shelter.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bangkit.shelter.R
import com.bangkit.shelter.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        with(binding) {
            btnResetPassword.setOnClickListener {
                val email = medEmail.text.toString().trim()
                android.util.Log.d("test", email)
                if(email != null){
                    auth.sendPasswordResetEmail(email).addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            android.widget.Toast.makeText(
                                this@ResetPasswordActivity,
                                "Check your email to reset your password",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            android.widget.Toast.makeText(
                                this@ResetPasswordActivity,
                                "Failed to Reset Password",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
            btnBack.setOnClickListener{
                startActivity(Intent(this@ResetPasswordActivity, com.bangkit.shelter.ui.auth.LoginActivity::class.java))
            }
        }
    }
}