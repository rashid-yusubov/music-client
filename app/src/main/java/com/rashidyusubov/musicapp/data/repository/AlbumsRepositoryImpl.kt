package com.rashidyusubov.musicapp.data.repository

import com.rashidyusubov.musicapp.data.mapper.toDomain
import com.rashidyusubov.musicapp.data.remote.api.AlbumsApi
import com.rashidyusubov.musicapp.domain.model.Album
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.AlbumsRepository
import javax.inject.Inject

class AlbumsRepositoryImpl @Inject constructor(
    private val api: AlbumsApi
) : AlbumsRepository {

    override suspend fun getAlbums(): List<Album> {

        return api.getAlbums().map { it.toDomain() }
    }

    override suspend fun getAlbumById(id: Int): Album {

        return api.getAlbumById(id).toDomain()
    }

    override suspend fun getAlbumTracks(id: Int): List<Track> {

        return api.getAlbumTracks(id).map { it.toDomain() }
    }
}