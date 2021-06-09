package com.bangkit.shelter.ui.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.shelter.R
import com.bangkit.shelter.data.entity.User
import com.bangkit.shelter.databinding.ItemUserBinding
import com.bangkit.shelter.ui.chat.ChatActivity

class UserAdapter(private val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position], position)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val list_anim = arrayListOf(
            "Rabbit-Anonymous",
            "Monkey-Anonymous",
            "Zebra-Anonymous",
            "Giraffe-Anonymous",
            "Fish-Anonymous",
            "Horse-Anonymous",
            "Panda-Anonymous",
            "Duck-Anonymous",
            "Snake-Anonymous"
        )
        fun bind(user: User, position: Int) {
            with(binding) {
                Log.d("testing", position.toString())
                Log.d("testing", list_anim.size.toString())
                if (position < list_anim.size){
                    userName.text = list_anim[position]
                }

                userImage.setBackgroundResource(R.drawable.profile_image)
                itemView.setOnClickListener {
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("userId", user.userId)
                    intent.putExtra("username", list_anim[position])
                    context.startActivity(intent)
                }

            }
        }
    }
}