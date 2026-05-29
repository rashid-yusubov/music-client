package com.rashidyusubov.musicapp.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.CreatePlaylistUseCase
import com.rashidyusubov.musicapp.domain.usecase.DeletePlaylistUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            PlaylistsUiState()
        )

    val state =
        _state.asStateFlow()

    init {

        loadPlaylists()
    }

    fun loadPlaylists() {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true
                    )

                val playlists =
                    getPlaylistsUseCase()

                _state.value =
                    _state.value.copy(
                        playlists = playlists,
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

    fun createPlaylist(
        title: String
    ) {

        viewModelScope.launch {

            createPlaylistUseCase(
                title = title,
                description = null
            )

            loadPlaylists()
        }
    }

    fun deletePlaylist(
        playlistId: Int
    ) {

        viewModelScope.launch {

            deletePlaylistUseCase(
                playlistId
            )

            loadPlaylists()
        }
    }
}