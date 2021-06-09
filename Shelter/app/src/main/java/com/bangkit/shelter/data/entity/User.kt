package com.bangkit.shelter.data.entity

data class User(
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val profile_picture: String ="",
    val personality: String? = "",
){
    constructor(userId: String, username: String, email: String) : this(userId, username)
}