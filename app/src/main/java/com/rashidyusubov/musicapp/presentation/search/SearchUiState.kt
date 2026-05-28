package com.rashidyusubov.musicapp.presentation.search

data class SearchUiState(

    val query: String = "",

    val isLoading: Boolean = false,

    val tracks: List<String> = emptyList(),

    val error: String? = null
)