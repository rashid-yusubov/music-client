package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.domain.model.Album
import com.rashidyusubov.musicapp.domain.model.Track

interface AlbumsRepository {

    suspend fun getAlbums(): List<Album>

    suspend fun getAlbumById(id: Int): Album

    suspend fun getAlbumTracks(id: Int): List<Track>
}