package com.rashidyusubov.musicapp.presentation.track

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TrackDetailsScreen(trackId: Int, viewModel: TrackDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(trackId) {

        viewModel.loadTrack(trackId)
    }

    val track = state.track

    if (track == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Text("Загрузка...")
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

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
            text = "Длительность: ${track.duration}"
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