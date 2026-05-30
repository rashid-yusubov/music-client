package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.PlaylistsRepository
import javax.inject.Inject

class GetPlaylistTracksUseCase @Inject constructor(
    private val repository: PlaylistsRepository
) {
    suspend operator fun invoke(playlistId: Int): List<Track> {
        return repository.getPlaylistTracks(playlistId)
    }
}
