package com.rashidyusubov.musicapp.data.repository

import com.rashidyusubov.musicapp.data.mapper.toDomain
import com.rashidyusubov.musicapp.data.remote.api.PlaylistsApi
import com.rashidyusubov.musicapp.data.remote.auth.FirebaseTokenProvider
import com.rashidyusubov.musicapp.domain.model.Playlist
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.PlaylistsRepository
import javax.inject.Inject

class PlaylistsRepositoryImpl @Inject constructor(
    private val api: PlaylistsApi,
    private val tokenProvider: FirebaseTokenProvider
) : PlaylistsRepository {

    override suspend fun getPlaylists(): List<Playlist> {

        val token = tokenProvider.getToken() ?: return emptyList()

        return api.getPlaylists(token).map { it.toDomain() }
    }

    override suspend fun getPlaylistTracks(playlistId: Int): List<Track> {
        val token = tokenProvider.getToken() ?: return emptyList()
        return api.getPlaylistTracks(playlistId, token).map { it.toDomain() }
    }

    override suspend fun createPlaylist(title: String, description: String?) {

        val token = tokenProvider.getToken() ?: return

        api.createPlaylist(
            token = token,
            title = title,
            description = description
        )
    }

    override suspend fun deletePlaylist(playlistId: Int) {

        val token = tokenProvider.getToken() ?: return

        api.deletePlaylist(playlistId = playlistId, token = token)
    }

    override suspend fun addTrackToPlaylist(playlistId: Int, trackId: Int) {

        val token = tokenProvider.getToken() ?: return

        api.addTrackToPlaylist(
            playlistId = playlistId,
            trackId = trackId,
            token = token
        )
    }

    override suspend fun removeTrackFromPlaylist(playlistId: Int, trackId: Int) {

        val token = tokenProvider.getToken() ?: return

        api.removeTrackFromPlaylist(
            playlistId = playlistId,
            trackId = trackId,
            token = token
        )
    }
}