package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class TracksApiImpl @Inject constructor(private val client: HttpClient) : TracksApi {

    override suspend fun searchTracks(query: String): List<TrackDto> {

        return client.get("$BASE_URL/tracks/search") { parameter("query", query) }.body()
    }

    override suspend fun getAllTracks(): List<TrackDto> {

        return client.get("$BASE_URL/tracks").body()
    }
}