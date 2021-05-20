package com.bangkit.shelter.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.shelter.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var activityChatBinding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityChatBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(activityChatBinding.root)
    }
}