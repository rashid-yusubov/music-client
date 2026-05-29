package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.domain.model.Artist

interface ArtistsRepository {

    suspend fun getArtists(): List<Artist>

    suspend fun getArtistById(
        id: Int
    ): Artist
}