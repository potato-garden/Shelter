package com.bangkit.shelter.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.data.entity.User
import com.bangkit.shelter.databinding.ActivitySignUpBinding
import com.bangkit.shelter.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        private val TAG = SignUpActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(activitySignUpBinding.root)

        with(activitySignUpBinding) {

            btnBack.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
            }
            btnSignin.setOnClickListener {
                val username = medName.text.toString()
                val email = medEmail.text.toString()
                val password = medPassword.text.toString()
                createAccount(email, password, username)
            }
        }
    }

    private fun createAccount(email: String, password: String, username: String) {
        // [START create_user_with_email]
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    val user_create = User(username, email)
                    FirebaseAuth.getInstance().currentUser?.let {
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(it.uid)
                            .setValue(user_create).addOnCompleteListener {
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "User has been registered sucessfuly",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{
                                    Toast.makeText(
                                        this@SignUpActivity,
                                        "Failed to register",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(this, "Sry Failed", Toast.LENGTH_SHORT).show()
                }
            }
        // [END create_user_with_email]
    }
}