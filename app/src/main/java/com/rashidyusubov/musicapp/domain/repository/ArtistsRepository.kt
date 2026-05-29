package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.domain.model.Album
import com.rashidyusubov.musicapp.domain.model.Artist
import com.rashidyusubov.musicapp.domain.model.Track

interface ArtistsRepository {

    suspend fun getArtists(): List<Artist>

    suspend fun getArtistById(id: Int): Artist

    suspend fun getArtistTracks(id: Int): List<Track>

    suspend fun getArtistAlbums(id: Int): List<Album>
}