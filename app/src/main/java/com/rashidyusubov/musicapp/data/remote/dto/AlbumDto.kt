package com.rashidyusubov.musicapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(

    val id: Int,

    val title: String,

    val artistId: Int,

    val coverUrl: String?,

    val releaseYear: Int
)