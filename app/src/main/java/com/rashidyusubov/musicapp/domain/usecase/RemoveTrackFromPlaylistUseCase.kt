package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.PlaylistsRepository
import javax.inject.Inject

class RemoveTrackFromPlaylistUseCase @Inject constructor(
    private val repository: PlaylistsRepository
) {

    suspend operator fun invoke(
        playlistId: Int,
        trackId: Int
    ) {

        repository.removeTrackFromPlaylist(
            playlistId,
            trackId
        )
    }
}