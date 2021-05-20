package com.bangkit.shelter.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.shelter.databinding.ActivityRatingBinding
import com.bangkit.shelter.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var activitySettingBinding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(activitySettingBinding.root)
    }
}