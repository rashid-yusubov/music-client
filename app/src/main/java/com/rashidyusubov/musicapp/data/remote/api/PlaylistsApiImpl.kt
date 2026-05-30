package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.data.remote.dto.CreatePlaylistRequest
import com.rashidyusubov.musicapp.data.remote.dto.PlaylistDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import javax.inject.Inject

class PlaylistsApiImpl @Inject constructor(
    private val client: HttpClient
) : PlaylistsApi {

    override suspend fun getPlaylists(token: String): List<PlaylistDto> {

        return client.get("${BASE_URL}playlists") {

            header(HttpHeaders.Authorization, "Bearer $token") }.body()
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