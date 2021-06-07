package com.bangkit.shelter.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.R
import com.bangkit.shelter.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    companion object {
        private val TAG = RegisterActivity::class.java.simpleName
        private const val KEY_USER_ID = "userId"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_PROFILE_PICTURE = "profile_picture"
        private const val KEY_PERSONALITY = "personality"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnRegister.setOnClickListener {
                val username = medName.text.toString()
                val email = medEmail.text.toString()
                val password = medPassword.text.toString()
                val confirmPassword = medConfirmPassword.text.toString()
                if (password != confirmPassword) {
                    medConfirmPassword.error = "Error password not same"
                } else {
                    createAccount(email, password, username)
                }
            }
        }
    }

    private fun createAccount(email: String, password: String, username: String) {
        binding.searchLoading.visibility = View.VISIBLE
        binding.btnRegister.visibility = View.GONE
        binding.btnBack.visibility = View.GONE
        binding.medConfirmPassword.visibility = View.GONE
        binding.medName.visibility = View.GONE
        binding.medEmail.visibility = View.GONE
        binding.medPassword.visibility = View.GONE
        binding.milEmail.visibility = View.GONE
        binding.milConfirmPassword.visibility = View.GONE
        binding.milName.visibility = View.GONE
        binding.milPassword.visibility = View.GONE
        binding.mtvWelcome.visibility = View.GONE
        binding.layoutRegister.setBackgroundResource(R.color.white_contrast)
        // [START create_user_with_email]
        val db = Firebase.firestore
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    var user: FirebaseUser? = auth.currentUser
                    var userId: String = user!!.uid
                    Log.d("test", userId)

                    val user_hash = hashMapOf(
                        KEY_USER_ID to userId,
                        KEY_USERNAME to username,
                        KEY_EMAIL to email,
                        KEY_PROFILE_PICTURE to "",
                        KEY_PERSONALITY to "",
                    )

                    db.collection("User")
                        .document(userId)
                        .set(user_hash)
                        .addOnSuccessListener {
                            Toast.makeText(this@RegisterActivity, "User Save", Toast.LENGTH_SHORT)
                                .show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this@RegisterActivity,
                                "Failed ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)
                    databaseReference.setValue(user_hash).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            binding.searchLoading.visibility = View.GONE
                            Toast.makeText(
                                this@RegisterActivity,
                                "firebase load",
                                Toast.LENGTH_SHORT
                            ).show()
                            var intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Log.d("test", task.exception?.message.toString())
                            Toast.makeText(
                                this@RegisterActivity,
                                "fail ${task.exception?.message.toString()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    // Sign in success, update UI with the signed-in user's information

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