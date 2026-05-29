package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.domain.model.Album

interface AlbumsRepository {

    suspend fun getAlbums(): List<Album>

    suspend fun getAlbumById(id: Int): Album
}