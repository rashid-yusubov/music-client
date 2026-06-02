package com.rashidyusubov.musicapp.presentation.playlist.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.usecase.DeletePlaylistUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetPlaylistTracksUseCase
import com.rashidyusubov.musicapp.domain.usecase.RemoveTrackFromPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlaylistDetailsUiState(
    val tracks: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isDeleted: Boolean = false
)

@HiltViewModel
class PlaylistDetailsViewModel @Inject constructor(
    private val getPlaylistTracksUseCase: GetPlaylistTracksUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    private val removeTrackFromPlaylistUseCase: RemoveTrackFromPlaylistUseCase,
    private val getArtistsUseCase: com.rashidyusubov.musicapp.domain.usecase.GetArtistsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlaylistDetailsUiState())
    val state = _state.asStateFlow()

    fun loadTracks(playlistId: Int) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val tracks = getPlaylistTracksUseCase(playlistId)
                val artists = getArtistsUseCase()
                val artistMap = artists.associateBy { it.id }
                
                val enrichedTracks = tracks.map { track ->
                    track.copy(artistName = artistMap[track.artistId]?.name ?: track.artistName)
                }
                
                _state.value = _state.value.copy(tracks = enrichedTracks, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun deletePlaylist(playlistId: Int) {
        viewModelScope.launch {
            try {
                deletePlaylistUseCase(playlistId)
                _state.value = _state.value.copy(isDeleted = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun removeTrack(playlistId: Int, trackId: Int) {
        viewModelScope.launch {
            try {
                removeTrackFromPlaylistUseCase(playlistId, trackId)
                loadTracks(playlistId)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
