package com.bangkit.shelter.ui.rating

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.shelter.MainActivity
import com.bangkit.shelter.databinding.ActivityRatingBinding

class RatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRatingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddComment.setOnClickListener {
            startActivity(Intent(baseContext, AddCommentActivity::class.java))
        }
        binding.btnDone.setOnClickListener {
            startActivity(Intent(baseContext, MainActivity::class.java))
            finish()
        }
    }
}