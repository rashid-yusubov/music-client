package com.rashidyusubov.musicapp.presentation.artist.details

import com.rashidyusubov.musicapp.domain.model.Album
import com.rashidyusubov.musicapp.domain.model.Artist
import com.rashidyusubov.musicapp.domain.model.Track

data class ArtistDetailsUiState(

    val artist: Artist? = null,

    val tracks: List<Track> = emptyList(),

    val albums: List<Album> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)