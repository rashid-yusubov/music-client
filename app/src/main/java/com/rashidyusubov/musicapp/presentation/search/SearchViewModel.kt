package com.rashidyusubov.musicapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.SearchHistoryRepository
import com.rashidyusubov.musicapp.domain.usecase.SearchTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTracksUseCase: SearchTracksUseCase,
    private val historyRepository: SearchHistoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        SearchUiState()
    )

    val state = _state.asStateFlow()

    private var lastQuery = ""

    init {
        loadHistory()
    }

    fun updateQuery(query: String) {

        _state.value =
            _state.value.copy(
                query = query
            )
    }

    fun clearQuery() {

        _state.value =
            _state.value.copy(
                query = "",
                tracks = emptyList(),
                error = null
            )
    }

    fun search() {

        val query = _state.value.query

        if (query.isBlank()) return

        lastQuery = query

        viewModelScope.launch {

            try {

                _state.value =
                    _state.value.copy(
                        isLoading = true,
                        error = null
                    )

                val tracks =
                    searchTracksUseCase(query)

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

    fun retry() {

        if (lastQuery.isNotBlank()) {

            search()
        }
    }

    private fun loadHistory() {

        viewModelScope.launch {

            _state.value =
                _state.value.copy(
                    history = historyRepository.getHistory()
                )
        }
    }

    fun clearHistory() {

        viewModelScope.launch {

            historyRepository.clearHistory()

            loadHistory()
        }
    }

    fun saveTrackToHistory(
        track: Track
    ) {

        viewModelScope.launch {

            historyRepository.saveTrack(
                trackId = track.id,
                title = track.title
            )

            loadHistory()
        }
    }

    fun searchFromHistory(
        query: String
    ) {

        updateQuery(query)

        search()
    }
}