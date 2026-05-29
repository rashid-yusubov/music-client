package com.rashidyusubov.musicapp.domain.model

data class Playlist(

    val id: Int,

    val userId: Int,

    val title: String,

    val description: String?,

    val coverUrl: String?
)