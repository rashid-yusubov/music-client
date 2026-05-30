package com.rashidyusubov.musicapp.domain.model

data class Track(
    val id: Int,
    val title: String,
    val artistId: Int,
    val artistName: String? = null,
    val albumId: Int,
    val duration: Int,
    val genre: String,
    val audioUrl: String,
    val coverUrl: String?
)