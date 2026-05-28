package com.rashidyusubov.musicapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rashidyusubov.musicapp.data.local.dao.SearchHistoryDao
import com.rashidyusubov.musicapp.data.local.entity.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class], version = 1
)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao
}