package com.bangkit.shelter.ui.call

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.shelter.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {
    private lateinit var activityCallBinding: ActivityCallBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCallBinding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(activityCallBinding.root)
    }
}