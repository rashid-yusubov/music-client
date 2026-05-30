package com.rashidyusubov.musicapp.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.AddTrackToPlaylistUseCase
import com.rashidyusubov.musicapp.domain.usecase.CreatePlaylistUseCase
import com.rashidyusubov.musicapp.domain.usecase.DeletePlaylistUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetPlaylistsUseCase
import com.rashidyusubov.musicapp.domain.usecase.RemoveTrackFromPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val addTrackToPlaylistUseCase: AddTrackToPlaylistUseCase,
    private val removeTrackFromPlaylistUseCase: RemoveTrackFromPlaylistUseCase
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
        title: String,
        description: String? = null
    ) {
        viewModelScope.launch {

            try {

                createPlaylistUseCase(
                    title = title,
                    description = description
                )

                loadPlaylists()

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        error = e.message
                    )
            }
        }
    }

    fun deletePlaylist(
        playlistId: Int
    ) {
        viewModelScope.launch {

            try {

                deletePlaylistUseCase(
                    playlistId
                )

                loadPlaylists()

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        error = e.message
                    )
            }
        }
    }

    fun addTrackToPlaylist(
        playlistId: Int,
        trackId: Int
    ) {
        viewModelScope.launch {

            try {

                addTrackToPlaylistUseCase(
                    playlistId = playlistId,
                    trackId = trackId
                )
                _state.value = _state.value.copy(trackAdded = true)
                // Reset after some time
                kotlinx.coroutines.delay(2000)
                _state.value = _state.value.copy(trackAdded = false)

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        error = e.message
                    )
            }
        }
    }

    fun removeTrackFromPlaylist(
        playlistId: Int,
        trackId: Int
    ) {
        viewModelScope.launch {

            try {

                removeTrackFromPlaylistUseCase(
                    playlistId = playlistId,
                    trackId = trackId
                )

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        error = e.message
                    )
            }
        }
    }
}