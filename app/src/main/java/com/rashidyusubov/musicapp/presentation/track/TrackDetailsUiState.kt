package com.rashidyusubov.musicapp.presentation.track

import com.rashidyusubov.musicapp.domain.model.Track

data class TrackDetailsUiState(

    val track: Track? = null,

    val isLoading: Boolean = false,

    val error: String? = null
)