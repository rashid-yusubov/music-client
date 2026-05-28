package com.rashidyusubov.musicapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(

    @PrimaryKey
    val trackId: Int,

    val title: String,

    val createdAt: Long
)