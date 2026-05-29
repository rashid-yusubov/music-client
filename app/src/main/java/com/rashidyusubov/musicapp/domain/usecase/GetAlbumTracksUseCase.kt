package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.AlbumsRepository
import javax.inject.Inject

class GetAlbumTracksUseCase @Inject constructor(
    private val repository: AlbumsRepository
) {

    suspend operator fun invoke(
        id: Int
    ) = repository.getAlbumTracks(id)
}