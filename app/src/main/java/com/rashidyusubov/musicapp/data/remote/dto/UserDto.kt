package com.rashidyusubov.musicapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(

    val id: Int,

    val firebaseUid: String,

    val email: String,

    val username: String,

    val avatarUrl: String?
)