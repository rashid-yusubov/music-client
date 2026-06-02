package com.rashidyusubov.musicapp.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.GetFavoritesUseCase
import com.rashidyusubov.musicapp.domain.usecase.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val getArtistsUseCase: com.rashidyusubov.musicapp.domain.usecase.GetArtistsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LibraryUiState())

    val state = _state.asStateFlow()

    fun loadFavorites() {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true,
                        error = null
                    )

                val tracks = getFavoritesUseCase()
                val artists = getArtistsUseCase()
                val artistMap = artists.associateBy { it.id }
                
                val enrichedTracks = tracks.map { track ->
                    track.copy(artistName = artistMap[track.artistId]?.name ?: track.artistName)
                }

                _state.value =
                    _state.value.copy(
                        isLoading = false,
                        tracks = enrichedTracks
                    )

            } catch (e: Exception) {

                _state.value =
                    _state.value.copy(
                        isLoading = false,
                        error = e.message
                    )
            }
        }
    }

    fun removeFromFavorites(
        trackId: Int
    ) {

        viewModelScope.launch {

            try {

                removeFromFavoritesUseCase(trackId)

                loadFavorites()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}