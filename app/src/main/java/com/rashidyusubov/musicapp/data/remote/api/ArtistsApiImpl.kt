package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.ArtistDto
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
}