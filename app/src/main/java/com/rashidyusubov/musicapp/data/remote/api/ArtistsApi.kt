package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.data.remote.dto.ArtistDto

interface ArtistsApi {

    suspend fun getArtists(): List<ArtistDto>

    suspend fun getArtistById(
        id: Int
    ): ArtistDto
}