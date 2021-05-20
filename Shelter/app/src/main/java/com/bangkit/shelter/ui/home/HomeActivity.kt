package com.bangkit.shelter.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.databinding.ActivityHomeBinding
import com.bangkit.shelter.ui.auth.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var activityHomeBinding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        var signInAccount: GoogleSignInAccount? =
            GoogleSignIn.getLastSignedInAccount(this@HomeActivity)

        with(activityHomeBinding) {
            btnLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@HomeActivity, MainActivity::class.java)
                startActivity(intent)
            }
            if (signInAccount != null) {
                tvName.text = signInAccount.displayName
                tvMail.text = signInAccount.email
            }
        }
    }
}