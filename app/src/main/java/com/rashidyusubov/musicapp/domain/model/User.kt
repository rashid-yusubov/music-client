package com.rashidyusubov.musicapp.domain.model

data class User(

    val id: Int,

    val email: String,

    val username: String,

    val avatarUrl: String?
)