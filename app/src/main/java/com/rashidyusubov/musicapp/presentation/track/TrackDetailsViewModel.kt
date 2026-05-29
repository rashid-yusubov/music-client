package com.rashidyusubov.musicapp.presentation.track

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.SearchHistoryRepository
import com.rashidyusubov.musicapp.domain.usecase.AddToFavoritesUseCase
import com.rashidyusubov.musicapp.domain.usecase.GetTrackByIdUseCase
import com.rashidyusubov.musicapp.domain.usecase.SearchTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackDetailsViewModel @Inject constructor(

    private val getTrackByIdUseCase: GetTrackByIdUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TrackDetailsUiState())

    val state = _state.asStateFlow()

    fun loadTrack(trackId: Int) {

        viewModelScope.launch {

            try {

                _state.value = _state.value.copy(isLoading = true)

                val track = getTrackByIdUseCase(trackId)

                _state.value = _state.value.copy(track = track, isLoading = false)

            } catch (e: Exception) {

                _state.value = _state.value.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun addToFavorites() {

        val trackId =
            _state.value.track?.id
                ?: return

        viewModelScope.launch {

            try {

                println("ADD FAVORITE: $trackId")

                addToFavoritesUseCase(trackId)

                println("FAVORITE ADDED")

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}