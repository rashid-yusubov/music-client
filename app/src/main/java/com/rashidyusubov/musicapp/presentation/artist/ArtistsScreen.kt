package com.rashidyusubov.musicapp.presentation.artist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.components.ArtistItem
import com.rashidyusubov.musicapp.presentation.components.ErrorContent
import com.rashidyusubov.musicapp.presentation.components.LoadingContent

@Composable
fun ArtistsScreen(
    viewModel: ArtistsViewModel =
        hiltViewModel()
) {

    val state by
    viewModel.state.collectAsState()

    if (state.isLoading) {

        LoadingContent()

        return
    }

    state.error?.let {

        ErrorContent(
            message = it
        )

        return
    }

    LazyColumn {

        items(state.artists) { artist ->

            ArtistItem(
                artist = artist,
                onClick = {

                }
            )
        }
    }
}