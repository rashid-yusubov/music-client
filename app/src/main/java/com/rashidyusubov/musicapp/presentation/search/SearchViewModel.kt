package com.rashidyusubov.musicapp.presentation.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {

    private val _state =
        MutableStateFlow(SearchUiState())

    val state =
        _state.asStateFlow()

    fun updateQuery(query: String) {

        _state.value =
            _state.value.copy(
                query = query
            )
    }

    fun clearQuery() {

        _state.value =
            _state.value.copy(
                query = ""
            )
    }

    fun fakeSearch() {

        if (_state.value.query.isBlank()) return

        _state.value =
            _state.value.copy(
                isLoading = true
            )

        _state.value =
            _state.value.copy(
                isLoading = false,

                tracks = listOf(
                    "Mock Track 1",
                    "Mock Track 2",
                    "Mock Track 3"
                )
            )
    }
}