package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.data.local.entity.SearchHistoryEntity

interface SearchHistoryRepository {

    suspend fun getHistory(): List<SearchHistoryEntity>

    suspend fun saveTrack(
        trackId: Int,
        title: String
    )

    suspend fun clearHistory()
}