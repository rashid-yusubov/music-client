package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.AlbumDto
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class AlbumsApiImpl @Inject constructor(
    private val client: HttpClient
) : AlbumsApi {

    override suspend fun getAlbums(): List<AlbumDto> {

        return client.get("${BASE_URL}albums").body()
    }

    override suspend fun getAlbumById(id: Int): AlbumDto {

        return client.get("${BASE_URL}albums/$id").body()
    }

    override suspend fun getAlbumTracks(id: Int): List<TrackDto> {

        return client.get("${BASE_URL}albums/$id/tracks").body()
    }
}