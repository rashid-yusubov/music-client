package com.rashidyusubov.musicapp.presentation.album

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AlbumsUiState())

    val state = _state.asStateFlow()

    init {
        loadAlbums()
    }

    private fun loadAlbums() {

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true
                    )

                val albums = getAlbumsUseCase()

                _state.value =
                    _state.value.copy(
                        albums = albums,
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