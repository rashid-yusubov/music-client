package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.domain.model.Track

interface TracksRepository {

    suspend fun searchTracks(query: String): List<Track>
}