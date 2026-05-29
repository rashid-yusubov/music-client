package com.rashidyusubov.musicapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDto(

    val id: Int,

    val userId: Int,

    val title: String,

    val description: String?,

    val coverUrl: String?
)