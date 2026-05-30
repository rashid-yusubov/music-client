package com.rashidyusubov.musicapp.presentation.track

import com.rashidyusubov.musicapp.domain.model.Track

data class TrackDetailsUiState(

    val track: Track? = null,

    val isFavorite: Boolean = false,

    val isLoading: Boolean = false,

    val error: String? = null
)
