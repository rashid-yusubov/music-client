package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import javax.inject.Inject

class TracksApiImpl @Inject constructor(private val client: HttpClient) : TracksApi {

    override suspend fun searchTracks(query: String): List<TrackDto> {

        return client.get("$BASE_URL/tracks/search") { parameter("query", query) }.body()
    }

    override suspend fun getAllTracks(): List<TrackDto> {

        return client.get("$BASE_URL/tracks").body()
    }

    override suspend fun getTrackById(id: Int): TrackDto {

        return client.get("$BASE_URL/tracks/$id").body()
    }

    override suspend fun getFavorites(token: String): List<TrackDto> {

        return client.get("$BASE_URL/favorites") {

            header(HttpHeaders.Authorization, "Bearer $token") }.body()
    }

    override suspend fun addToFavorites(trackId: Int, token: String) {

        client.post("$BASE_URL/favorites/$trackId") {

            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    override suspend fun removeFromFavorites(trackId: Int, token: String) {

        client.delete("$BASE_URL/favorites/$trackId") {

            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}