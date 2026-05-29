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
import androidx.compose.runtime.remember
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.rashidyusubov.musicapp.presentation.player.AudioPlayerManager
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.core.network.BASE_URL
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

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

    val context = LocalContext.current

    val player = remember {

        AudioPlayerManager(context)
    }

    var isPlaying by remember {
        mutableStateOf(false)
    }

    var currentPosition by remember {
        mutableStateOf(0L)
    }

    DisposableEffect(Unit) {

        onDispose {

            player.release()
        }
    }

    LaunchedEffect(isPlaying) {

        while (isPlaying) {

            currentPosition =
                player.currentPosition()

            delay(500)
        }
    }

    val minutes = track.duration / 60
    val seconds = track.duration % 60

    val progress = if (player.duration() > 0) currentPosition.toFloat() / player.duration().toFloat()

        else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = track.coverUrl,
            contentDescription = track.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = track.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = track.genre,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                "%d:%02d".format(
                    currentPosition / 60000,
                    (currentPosition / 1000) % 60
                )
            )

            Text(
                "%d:%02d".format(
                    minutes,
                    seconds
                )
            )
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = {

                if (isPlaying) {

                    player.pause()

                    isPlaying = false

                } else {

                    player.play(
                        BASE_URL +
                                track.audioUrl.removePrefix("/")
                    )

                    isPlaying = true
                }
            }
        ) {

            Text(
                text =
                    if (isPlaying)
                        "⏸ Пауза"
                    else
                        "▶ Воспроизвести"
            )
        }
    }
}