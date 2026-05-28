package com.rashidyusubov.musicapp.presentation.search

import com.rashidyusubov.musicapp.data.local.entity.SearchHistoryEntity
import com.rashidyusubov.musicapp.domain.model.Track

data class SearchUiState(

    val query: String = "",

    val isLoading: Boolean = false,

    val tracks: List<Track> = emptyList(),

    val history: List<SearchHistoryEntity> = emptyList(),

    val error: String? = null
)