package com.rashidyusubov.musicapp.presentation.track

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun TrackDetailsScreen(trackId: Int, viewModel: TrackDetailsViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(trackId) {

        viewModel.loadTrack(trackId)
    }

    if (state.isLoading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator()
        }

        return
    }

    state.error?.let {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Ошибка загрузки трека"
            )
        }

        return
    }

    val track = state.track ?: return

    val minutes = track.duration / 60
    val seconds = track.duration % 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        AsyncImage(
            model = track.coverUrl,
            contentDescription = track.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = track.title
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Жанр: ${track.genre}"
        )

        Text(
            text = "Длительность: %d:%02d".format(
                minutes,
                seconds
            )
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {

            }
        ) {

            Text(
                text = "Воспроизвести"
            )
        }
    }
}