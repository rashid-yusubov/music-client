package com.rashidyusubov.musicapp.presentation.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.components.EmptyContent
import com.rashidyusubov.musicapp.presentation.components.ErrorContent
import com.rashidyusubov.musicapp.presentation.components.LoadingContent
import com.rashidyusubov.musicapp.presentation.components.TrackItem

@Composable
fun LibraryScreen(
    onTrackClick: (Int) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.loadFavorites()
    }

    if (state.isLoading) {

        LoadingContent()

        return
    }

    state.error?.let {

        ErrorContent(
            message = "Ошибка загрузки избранного",

            onRetry = {

                viewModel.loadFavorites()
            }
        )

        return
    }

    if (state.tracks.isEmpty()) {

        EmptyContent(
            message = "Избранных треков пока нет"
        )

        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        items(state.tracks) { track ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Box(
                    modifier = Modifier.weight(1f)
                ) {

                    TrackItem(
                        track = track,
                        onClick = {

                            onTrackClick(track.id)
                        }
                    )
                }

                TextButton(
                    onClick = {

                        viewModel.removeFromFavorites(
                            track.id
                        )
                    }
                ) {

                    Text("💔")
                }
            }
        }
    }
}