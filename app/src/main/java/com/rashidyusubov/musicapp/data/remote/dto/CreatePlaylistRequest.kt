package com.rashidyusubov.musicapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreatePlaylistRequest(

    val title: String,

    val description: String?
)