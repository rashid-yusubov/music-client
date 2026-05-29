package com.rashidyusubov.musicapp.presentation.library

import com.rashidyusubov.musicapp.domain.model.Track

data class LibraryUiState(

    val isLoading: Boolean = false,

    val tracks: List<Track> = emptyList(),

    val error: String? = null
)