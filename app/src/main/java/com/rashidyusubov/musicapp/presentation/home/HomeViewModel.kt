package com.rashidyusubov.musicapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.GetAllTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTracksUseCase: GetAllTracksUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow(
            HomeUiState()
        )

    val state =
        _state.asStateFlow()

    init {

        loadTracks()
    }

    private fun loadTracks() {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true
                    )

                val tracks =
                    getAllTracksUseCase()

                _state.value =
                    _state.value.copy(
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