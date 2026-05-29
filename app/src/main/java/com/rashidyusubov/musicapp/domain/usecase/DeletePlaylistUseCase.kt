package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.PlaylistsRepository
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    private val repository: PlaylistsRepository
) {

    suspend operator fun invoke(
        playlistId: Int
    ) {

        repository.deletePlaylist(
            playlistId
        )
    }
}