package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.CreatePlaylistRequest
import com.rashidyusubov.musicapp.data.remote.dto.PlaylistDto
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import javax.inject.Inject

class PlaylistsApiImpl @Inject constructor(
    private val client: HttpClient
) : PlaylistsApi {

    override suspend fun getPlaylists(token: String): List<PlaylistDto> {
        val response: HttpResponse = client.get("${BASE_URL}playlists") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        return try {
            if (response.status.isSuccess()) {
                response.body<List<PlaylistDto>>()
            } else {
                throw Exception("Ошибка сервера: ${response.status.value}")
            }
        } catch (e: Exception) {
            if (response.status == HttpStatusCode.NotFound) {
                emptyList()
            } else {
                throw e
            }
        }
    }

    override suspend fun getPlaylistTracks(playlistId: Int, token: String): List<TrackDto> {
        val response: HttpResponse = client.get("${BASE_URL}playlists/$playlistId/tracks") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        return try {
            if (response.status.isSuccess()) {
                response.body<List<TrackDto>>()
            } else {
                throw Exception("Ошибка сервера: ${response.status.value}")
            }
        } catch (e: Exception) {
            if (response.status == HttpStatusCode.NotFound) {
                emptyList()
            } else {
                throw e
            }
        }
    }

    override suspend fun createPlaylist(token: String, title: String, description: String?) {

        client.post("${BASE_URL}playlists") {

            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(
                CreatePlaylistRequest(
                    title = title,
                    description = description
                )
            )
        }
    }

    override suspend fun deletePlaylist(playlistId: Int, token: String) {

        client.delete("${BASE_URL}playlists/$playlistId") {

            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    override suspend fun addTrackToPlaylist(playlistId: Int, trackId: Int, token: String) {

        client.post("${BASE_URL}playlists/$playlistId/tracks/$trackId") {

            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    override suspend fun removeTrackFromPlaylist(playlistId: Int, trackId: Int, token: String) {

        client.delete("${BASE_URL}playlists/$playlistId/tracks/$trackId") {

            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}