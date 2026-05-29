package com.rashidyusubov.musicapp.presentation.album.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.GetAlbumByIdUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetAlbumTracksUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetArtistByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailsViewModel @Inject constructor(
    private val getAlbumByIdUseCase: GetAlbumByIdUseCase,
    private val getAlbumTracksUseCase: GetAlbumTracksUseCase,
    private val getArtistByIdUseCase: GetArtistByIdUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            AlbumDetailsUiState()
        )

    val state =
        _state.asStateFlow()

    fun loadAlbum(
        albumId: Int
    ) {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true
                    )

                val album = getAlbumByIdUseCase(albumId)

                val artist = getArtistByIdUseCase(album.artistId)

                val tracks = getAlbumTracksUseCase(albumId)

                _state.value =
                    _state.value.copy(
                        album = album,
                        artist = artist,
                        tracks = tracks,
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