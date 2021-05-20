package com.bangkit.shelter.ui.rating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.shelter.databinding.ActivityRatingBinding

class RatingActivity : AppCompatActivity() {
    private lateinit var activityRatingBinding: ActivityRatingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRatingBinding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(activityRatingBinding.root)
    }
}