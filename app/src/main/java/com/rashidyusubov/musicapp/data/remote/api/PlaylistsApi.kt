package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.data.remote.dto.PlaylistDto
import com.rashidyusubov.musicapp.data.remote.dto.TrackDto

interface PlaylistsApi {

    suspend fun getPlaylists(token: String): List<PlaylistDto>

    suspend fun getPlaylistTracks(playlistId: Int, token: String): List<TrackDto>

    suspend fun createPlaylist(token: String, title: String, description: String?)

    suspend fun deletePlaylist(playlistId: Int, token: String)

    suspend fun addTrackToPlaylist(playlistId: Int, trackId: Int, token: String)

    suspend fun removeTrackFromPlaylist(playlistId: Int, trackId: Int, token: String)
}