package com.rashidyusubov.musicapp.data.repository

import com.rashidyusubov.musicapp.data.local.dao.SearchHistoryDao
import com.rashidyusubov.musicapp.data.local.entity.SearchHistoryEntity
import com.rashidyusubov.musicapp.domain.repository.SearchHistoryRepository
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val dao: SearchHistoryDao
) : SearchHistoryRepository {

    override suspend fun getHistory(): List<SearchHistoryEntity> {

        return dao.getHistory()
    }

    override suspend fun saveTrack(
        trackId: Int,
        title: String
    ) {

        dao.insert(
            SearchHistoryEntity(
                trackId = trackId,
                title = title,
                createdAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun clearHistory() {

        dao.clearHistory()
    }
}