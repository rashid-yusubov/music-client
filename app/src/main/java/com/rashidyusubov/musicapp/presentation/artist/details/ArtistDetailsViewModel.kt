package com.rashidyusubov.musicapp.presentation.artist.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.GetArtistAlbumsUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetArtistByIdUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetArtistTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailsViewModel @Inject constructor(
    private val getArtistByIdUseCase: GetArtistByIdUseCase,
    private val getArtistTracksUseCase: GetArtistTracksUseCase,
    private val getArtistAlbumsUseCase: GetArtistAlbumsUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            ArtistDetailsUiState()
        )

    val state =
        _state.asStateFlow()

    fun loadArtist(
        artistId: Int
    ) {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true
                    )

                val artist =
                    getArtistByIdUseCase(
                        artistId
                    )

                val tracks =
                    getArtistTracksUseCase(
                        artistId
                    )

                val albums =
                    getArtistAlbumsUseCase(
                        artistId
                    )
                
                val enrichedTracks = tracks.map { track ->
                    track.copy(artistName = artist.name)
                }

                _state.value =
                    _state.value.copy(
                        artist = artist,
                        tracks = enrichedTracks,
                        albums = albums,
                        isLoading = false
                    )

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        error = e.message,
                        isLoading = false
                    )
            }
        }
    }
}
