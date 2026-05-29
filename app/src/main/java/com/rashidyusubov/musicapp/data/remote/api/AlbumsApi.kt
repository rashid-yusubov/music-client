package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.data.remote.dto.AlbumDto

interface AlbumsApi {

    suspend fun getAlbums(): List<AlbumDto>

    suspend fun getAlbumById(id: Int): AlbumDto
}