package com.rashidyusubov.musicapp.domain.usecase

import com.rashidyusubov.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class SearchTracksUseCase @Inject constructor(
    private val repository: TracksRepository
) {

    suspend operator fun invoke(query: String) = repository.searchTracks(query)
}