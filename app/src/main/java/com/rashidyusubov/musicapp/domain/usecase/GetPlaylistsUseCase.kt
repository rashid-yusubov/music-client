package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.PlaylistsRepository
import javax.inject.Inject

class GetPlaylistsUseCase @Inject constructor(
    private val repository: PlaylistsRepository
) {

    suspend operator fun invoke() = repository.getPlaylists()
}