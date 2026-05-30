package com.rashidyusubov.musicapp.presentation.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.SearchHistoryRepository
import com.rashidyusubov.musicapp.domain.usecase.AddToFavoritesUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetFavoritesUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetTrackByIdUseCase
import com.rashidyusubov.musicapp.domain.usecase.RemoveFromFavoritesUseCase
import com.rashidyusubov.musicapp.domain.usecase.SearchTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackDetailsViewModel @Inject constructor(
    private val getTrackByIdUseCase: GetTrackByIdUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getArtistByIdUseCase: com.rashidyusubov.musicapp.domain.usecase.GetArtistByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TrackDetailsUiState())

    val state = _state.asStateFlow()

    fun loadTrack(trackId: Int) {

        viewModelScope.launch {

            try {

                _state.value = _state.value.copy(isLoading = true)

                val track = getTrackByIdUseCase(trackId)
                val favorites = getFavoritesUseCase()
                val isFavorite = favorites.any { it.id == trackId }
                
                val artist = try {
                    getArtistByIdUseCase(track.artistId)
                } catch (e: Exception) {
                    null
                }
                
                val enrichedTrack = track.copy(artistName = artist?.name ?: track.genre)

                _state.value = _state.value.copy(
                    track = enrichedTrack,
                    isFavorite = isFavorite,
                    isLoading = false
                )

            } catch (e: Exception) {

                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun toggleFavorite() {
        val trackId = _state.value.track?.id ?: return
        val isFavorite = _state.value.isFavorite

        viewModelScope.launch {
            try {
                if (isFavorite) {
                    removeFromFavoritesUseCase(trackId)
                } else {
                    addToFavoritesUseCase(trackId)
                }
                _state.value = _state.value.copy(isFavorite = !isFavorite)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}