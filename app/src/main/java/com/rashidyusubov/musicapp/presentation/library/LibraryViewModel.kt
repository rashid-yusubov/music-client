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
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
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

                _state.value =
                    _state.value.copy(
                        isLoading = false,
                        tracks = tracks
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