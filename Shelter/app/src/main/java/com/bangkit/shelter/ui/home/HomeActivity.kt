package com.bangkit.shelter.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.data.entity.User
import com.bangkit.shelter.databinding.ActivityHomeBinding
import com.bangkit.shelter.ui.auth.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userId = currentUser?.uid


        if (currentUser == null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            if (userId != null) {
                reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userProfile = snapshot.getValue(User::class.java) as User
                        if (userProfile != null) {
                            activityHomeBinding.tvName.text = userProfile.username
                            activityHomeBinding.tvMail.text = userProfile.email
                        } else {
                            activityHomeBinding.tvName.text = currentUser.displayName
                            activityHomeBinding.tvMail.text = currentUser.email
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@HomeActivity, "Failed", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)

        with(activityHomeBinding) {
            btnLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@HomeActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}