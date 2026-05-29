package com.rashidyusubov.musicapp.presentation.home

import com.rashidyusubov.musicapp.domain.model.Track

data class HomeUiState(

    val tracks: List<Track> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)