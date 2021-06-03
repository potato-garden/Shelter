package com.bangkit.shelter.data.entity

data class User(
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val profile_picture: String ="https://firebasestorage.googleapis.com/v0/b/potato-garden.appspot.com/o/profile.png?alt=media&token=cb1a78e1-58e4-4dfd-a108-32719245c86b",
    val personality: String? = "",
){
    constructor(userId: String, username: String, email: String) : this(userId, username)
}