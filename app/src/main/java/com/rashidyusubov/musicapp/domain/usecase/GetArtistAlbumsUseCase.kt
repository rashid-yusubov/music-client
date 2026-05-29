package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.ArtistsRepository
import javax.inject.Inject

class GetArtistAlbumsUseCase @Inject constructor(
    private val repository: ArtistsRepository
) {

    suspend operator fun invoke(id: Int) = repository.getArtistAlbums(id)
}