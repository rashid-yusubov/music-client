package com.rashidyusubov.musicapp.presentation.album.details

import com.rashidyusubov.musicapp.domain.model.Album
import com.rashidyusubov.musicapp.domain.model.Artist
import com.rashidyusubov.musicapp.domain.model.Track

data class AlbumDetailsUiState(

    val album: Album? = null,

    val artist: Artist? = null,

    val tracks: List<Track> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)