package com.rashidyusubov.musicapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArtistDto(

    val id: Int,

    val name: String,

    val avatarUrl: String?,

    val description: String?
)