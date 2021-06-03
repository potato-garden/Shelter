package com.bangkit.shelter.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.shelter.R
import com.bangkit.shelter.data.entity.Chat
import com.bangkit.shelter.databinding.ActivityChatBinding
import com.bangkit.shelter.ui.auth.MainAuthActivity
import com.bangkit.shelter.ui.rating.RatingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    var firebaseUser: FirebaseUser? = null
    var reference: DatabaseReference? = null
    var chatList = ArrayList<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        var intent = getIntent()
        var userId = intent.getStringExtra("userId")
        var username = intent.getStringExtra("username")

        binding.chatRecyclerView.setHasFixedSize(true)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this@ChatActivity)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
        binding.tvUserName.text = username
        binding.imgExit.setOnClickListener {
            startActivity(Intent(baseContext, RatingActivity::class.java))
            finish()
        }

        binding.btnSendMessage.setOnClickListener {
            var message: String = binding.etMessage.text.toString().trim()

            if (message.isEmpty()){
                Toast.makeText(applicationContext, "message is empty", Toast.LENGTH_SHORT).show()
                binding.etMessage.setText("")
            }else{
                sendMessage(firebaseUser!!.uid,userId, message)
                binding.etMessage.setText("")
            }
        }
        readMessage(firebaseUser!!.uid, userId)
    }

    private fun sendMessage(senderId: String, receiverId: String, message: String) {
        var reference: DatabaseReference? = FirebaseDatabase.getInstance().getReference()

        var hashMap: HashMap<String, String> = HashMap()
        hashMap.put("senderId", senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message", message)

        reference!!.child("Chat").push().setValue(hashMap)

    }

    fun readMessage(senderId: String, receiverId: String) {
        val databaseReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapShot: DataSnapshot in snapshot.children) {
                    val chat = dataSnapShot.getValue(Chat::class.java)

                    if (chat!!.senderId.equals(senderId) && chat!!.receiverId.equals(receiverId) ||
                        chat!!.senderId.equals(receiverId) && chat!!.receiverId.equals(senderId)
                    ) {
                        chatList.add(chat)
                    }
                }
                Log.d("test", chatList.toString())

                val chatAdapter = ChatAdapter(chatList)

                binding.chatRecyclerView.adapter = chatAdapter
            }
        })
    }
}