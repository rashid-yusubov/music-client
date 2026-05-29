package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.PlaylistsRepository
import javax.inject.Inject

class CreatePlaylistUseCase @Inject constructor(
    private val repository: PlaylistsRepository
) {

    suspend operator fun invoke(
        title: String,
        description: String?
    ) {

        repository.createPlaylist(
            title,
            description
        )
    }
}