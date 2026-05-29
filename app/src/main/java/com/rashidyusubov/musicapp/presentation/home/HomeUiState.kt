package com.rashidyusubov.musicapp.presentation.home

import com.rashidyusubov.musicapp.domain.model.Artist
import com.rashidyusubov.musicapp.domain.model.Track

data class HomeUiState(

    val tracks: List<Track> = emptyList(),

    val artists: List<Artist> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)