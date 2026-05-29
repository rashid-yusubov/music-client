package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.data.remote.dto.AlbumDto
import com.rashidyusubov.musicapp.data.remote.dto.ArtistDto
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto

interface ArtistsApi {

    suspend fun getArtists(): List<ArtistDto>

    suspend fun getArtistById(id: Int): ArtistDto

    suspend fun getArtistTracks(id: Int): List<TrackDto>

    suspend fun getArtistAlbums(id: Int): List<AlbumDto>

}