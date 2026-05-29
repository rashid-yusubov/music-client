package com.rashidyusubov.musicapp.presentation.album

import com.rashidyusubov.musicapp.domain.model.Album

data class AlbumsUiState(

    val albums: List<Album> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)