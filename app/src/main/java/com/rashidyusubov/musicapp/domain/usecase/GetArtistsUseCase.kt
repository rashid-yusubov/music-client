package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.ArtistsRepository
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(
    private val repository: ArtistsRepository
) {

    suspend operator fun invoke() = repository.getArtists()
}