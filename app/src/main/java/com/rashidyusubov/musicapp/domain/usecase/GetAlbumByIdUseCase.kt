package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.AlbumsRepository
import javax.inject.Inject

class GetAlbumByIdUseCase @Inject constructor(
    private val repository: AlbumsRepository
) {

    suspend operator fun invoke(id: Int) = repository.getAlbumById(id)
}