package com.rashidyusubov.musicapp.presentation.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.loadFavorites()
    }

    if (state.isLoading) {

        CircularProgressIndicator()

        return
    }

    state.error?.let {

        Text(
            text = "Ошибка загрузки избранного"
        )

        return
    }

    if (state.tracks.isEmpty()) {

        Text(
            text = "Избранных треков пока нет"
        )

        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        items(state.tracks) { track ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { }
            ) {

                Text(
                    text = track.title
                )

                Text(
                    text = track.genre
                )
            }
        }
    }
}