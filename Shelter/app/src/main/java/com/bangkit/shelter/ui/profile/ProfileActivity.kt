package com.bangkit.shelter.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.shelter.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var activityProfileBinding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(activityProfileBinding.root)
    }
}