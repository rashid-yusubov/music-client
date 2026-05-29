package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.AlbumDto
import com.rashidyusubov.musicapp.data.remote.dto.ArtistDto
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class ArtistsApiImpl @Inject constructor(
    private val client: HttpClient
) : ArtistsApi {

    override suspend fun getArtists(): List<ArtistDto> {

        return client.get("${BASE_URL}artists").body()
    }

    override suspend fun getArtistById(id: Int): ArtistDto {

        return client.get("${BASE_URL}artists/$id").body() }

    override suspend fun getArtistTracks(id: Int): List<TrackDto> {

        return client.get("${BASE_URL}artists/$id/tracks").body()
    }

    override suspend fun getArtistAlbums(id: Int): List<AlbumDto> {

        return client.get("${BASE_URL}artists/$id/albums").body()
    }
}