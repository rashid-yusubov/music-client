package com.rashidyusubov.musicapp.presentation.playlist

import com.rashidyusubov.musicapp.domain.model.Playlist

data class PlaylistsUiState(

    val playlists: List<Playlist> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null,

    val trackAdded: Boolean = false
)
