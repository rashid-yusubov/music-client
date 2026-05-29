package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(private val repository: TracksRepository) {

    suspend operator fun invoke(trackId: Int) {

        repository.removeFromFavorites(trackId)
    }
}