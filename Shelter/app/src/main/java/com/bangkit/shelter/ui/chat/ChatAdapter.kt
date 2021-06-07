package com.bangkit.shelter.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.shelter.data.entity.Chat
import com.bangkit.shelter.databinding.ItemLeftBinding
import com.bangkit.shelter.databinding.ItemRightBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChatAdapter(private val chatList: ArrayList<Chat>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private val MESSAGE_TYPE_LEFT = 0
        private val MESSAGE_TYPE_RIGHT = 1
    }

    var firebaseUser: FirebaseUser? = null

    inner class rightViewHolder(
        private val binding: ItemRightBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun right_bind(chat: Chat) {
            with(binding) {
                tvMessage.text = chat.message
            }
        }
    }

    inner class leftViewHolder(
        private val binding: ItemLeftBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun left_bind(chat: Chat) {
            with(binding) {
                tvMessage.text = chat.message
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            val binding_right =
                ItemRightBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return rightViewHolder(binding_right)
        } else {
            val binding_left =
                ItemLeftBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
            return leftViewHolder(binding_left)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MESSAGE_TYPE_LEFT) {
            (holder as leftViewHolder).left_bind(chatList[position])
        } else if (getItemViewType(position) == MESSAGE_TYPE_RIGHT)
            (holder as rightViewHolder).right_bind(chatList[position])
    }

    override fun getItemCount(): Int = chatList.size

    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (chatList[position].senderId == firebaseUser!!.uid) {
            return MESSAGE_TYPE_RIGHT
        } else {
            return MESSAGE_TYPE_LEFT
        }
    }


}
