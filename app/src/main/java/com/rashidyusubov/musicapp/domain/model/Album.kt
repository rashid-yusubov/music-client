package com.rashidyusubov.musicapp.domain.model

data class Album(

    val id: Int,

    val title: String,

    val artistId: Int,

    val coverUrl: String?,

    val releaseYear: Int
)