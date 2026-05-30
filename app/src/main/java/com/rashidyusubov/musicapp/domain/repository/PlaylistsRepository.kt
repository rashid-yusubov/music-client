package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.domain.model.Playlist
import com.rashidyusubov.musicapp.domain.model.Track

interface PlaylistsRepository {

    suspend fun getPlaylists(): List<Playlist>

    suspend fun getPlaylistTracks(playlistId: Int): List<Track>

    suspend fun createPlaylist(title: String, description: String?)

    suspend fun deletePlaylist(playlistId: Int)

    suspend fun addTrackToPlaylist(playlistId: Int, trackId: Int)

    suspend fun removeTrackFromPlaylist(playlistId: Int, trackId: Int)
}