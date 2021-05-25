package com.bangkit.shelter.ui.home.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.shelter.R
import com.bangkit.shelter.data.entity.User
import com.bangkit.shelter.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        val userId = currentUser?.uid


        if (currentUser == null) {

        } else {
            if (userId != null) {
                reference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userProfile = snapshot.getValue(User::class.java) as User
                        if (userProfile != null) {
                            fragmentHomeBinding.tvName.text = userProfile.username
                            fragmentHomeBinding.tvMail.text = userProfile.email
                        } else {
                            fragmentHomeBinding.tvName.text = currentUser.displayName
                            fragmentHomeBinding.tvMail.text = currentUser.email
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("test","Failed")
                        /*Toast.makeText(this@HomeFragment, "Failed", Toast.LENGTH_LONG)*/
                    }

                })
            }
        }
    }


    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*with(activityHomeBinding) {
            btnLogout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this@HomeActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }*/
    }
}