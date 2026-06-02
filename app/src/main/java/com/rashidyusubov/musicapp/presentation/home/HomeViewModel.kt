package com.rashidyusubov.musicapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.GetAlbumsUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetAllTracksUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArtistsUseCase: GetArtistsUseCase,
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getAllTracksUseCase: GetAllTracksUseCase
    ) : ViewModel() {

    private val _state =
        MutableStateFlow(
            HomeUiState()
        )

    val state =
        _state.asStateFlow()

    init {

        loadContent()
    }

    fun loadContent() {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true
                    )

                val tracks =
                    getAllTracksUseCase()

                val artists =
                    getArtistsUseCase()

                val albums =
                    getAlbumsUseCase()

                val artistMap = artists.associateBy { it.id }
                val enrichedTracks = tracks.map { track ->
                    track.copy(artistName = artistMap[track.artistId]?.name)
                }

                _state.value =
                    _state.value.copy(
                        tracks = enrichedTracks,
                        artists = artists,
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