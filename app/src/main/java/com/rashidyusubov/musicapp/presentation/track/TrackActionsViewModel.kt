package com.rashidyusubov.musicapp.presentation.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.model.Playlist
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackActionsViewModel @Inject constructor(
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val addTrackToPlaylistUseCase: AddTrackToPlaylistUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists = _playlists.asStateFlow()

    private val _favorites = MutableStateFlow<List<Track>>(emptyList())
    val favorites = _favorites.asStateFlow()

    init {
        loadPlaylists()
        loadFavorites()
    }

    fun loadPlaylists() {
        viewModelScope.launch {
            try {
                _playlists.value = getPlaylistsUseCase()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            try {
                _favorites.value = getFavoritesUseCase()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun toggleFavorite(track: Track) {
        viewModelScope.launch {
            try {
                if (isFavorite(track.id)) {
                    removeFromFavoritesUseCase(track.id)
                } else {
                    addToFavoritesUseCase(track.id)
                }
                loadFavorites()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun addTrackToPlaylist(trackId: Int, playlistId: Int) {
        viewModelScope.launch {
            try {
                addTrackToPlaylistUseCase(playlistId, trackId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun isFavorite(trackId: Int): Boolean {
        return _favorites.value.any { it.id == trackId }
    }
}
