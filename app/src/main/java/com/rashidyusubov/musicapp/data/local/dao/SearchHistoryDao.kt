package com.rashidyusubov.musicapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rashidyusubov.musicapp.data.local.entity.SearchHistoryEntity

@Dao
interface SearchHistoryDao {

    @Query("""
        SELECT * FROM search_history
        ORDER BY createdAt DESC
        LIMIT 10
    """)
    suspend fun getHistory(): List<SearchHistoryEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insert(
        item: SearchHistoryEntity
    )

    @Query(
        "DELETE FROM search_history"
    )
    suspend fun clearHistory()
}