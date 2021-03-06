package com.bangkit.shelter.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.MainActivity
import com.bangkit.shelter.databinding.ActivityLoginBinding
import com.bangkit.shelter.ui.onboarding.QuestionActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnForgotPassword.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
            }
            btnLogin.setOnClickListener {
                val email = medEmail.text.toString()
                val password = medPassword.text.toString()
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        if (onBoardingFinished()) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val intent = Intent(this@LoginActivity, QuestionActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        user?.sendEmailVerification()
                        Toast.makeText(
                            this@LoginActivity,
                            "Check your email for verification",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        // [END sign_in_with_email]
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}