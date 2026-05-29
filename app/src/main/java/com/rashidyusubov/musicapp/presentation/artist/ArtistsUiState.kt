package com.rashidyusubov.musicapp.presentation.artist

import com.rashidyusubov.musicapp.domain.model.Artist

data class ArtistsUiState(

    val artists: List<Artist> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)