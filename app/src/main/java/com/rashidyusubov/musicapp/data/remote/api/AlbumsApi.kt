package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.data.remote.dto.AlbumDto
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto

interface AlbumsApi {

    suspend fun getAlbums(): List<AlbumDto>

    suspend fun getAlbumById(id: Int): AlbumDto

    suspend fun getAlbumTracks(id: Int): List<TrackDto>
}