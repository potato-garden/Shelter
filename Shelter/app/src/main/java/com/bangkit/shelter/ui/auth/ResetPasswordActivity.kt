package com.bangkit.shelter.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var activityResetPasswordBinding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResetPasswordBinding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(activityResetPasswordBinding.root)

        auth = FirebaseAuth.getInstance()
        with(activityResetPasswordBinding) {
            btnResetPassword.setOnClickListener {
                val email = medEmail.text.toString().trim()
                Log.d("test", email)
                if(email != null){
                    auth.sendPasswordResetEmail(email).addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                "Check your email to reset your password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                "Failed to Reset Password",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
            btnBack.setOnClickListener{
                startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
            }
        }

    }
}