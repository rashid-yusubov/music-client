package com.rashidyusubov.musicapp.presentation.track

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.presentation.player.AudioPlayerManager
import com.rashidyusubov.musicapp.presentation.playlist.PlaylistsViewModel
import kotlinx.coroutines.delay

@Composable
fun TrackDetailsScreen(trackId: Int, viewModel: TrackDetailsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val playlistsViewModel: PlaylistsViewModel = hiltViewModel()
    val playlistsState by playlistsViewModel.state.collectAsState()
    var showPlaylistDialog by remember { mutableStateOf(false) }

    if (showPlaylistDialog) {
        AlertDialog(
            onDismissRequest = { showPlaylistDialog = false },
            title = { Text("Добавить в плейлист") },
            text = {
                LazyColumn {
                    items(playlistsState.playlists) { playlist ->
                        TextButton(
                            onClick = {
                                playlistsViewModel.addTrackToPlaylist(playlist.id, trackId)
                                showPlaylistDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(playlist.title)
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showPlaylistDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    LaunchedEffect(trackId) {
        viewModel.loadTrack(trackId)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    val track = state.track ?: return
    val context = LocalContext.current
    val player = remember { AudioPlayerManager(context) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0L) }

    DisposableEffect(Unit) {
        onDispose { player.release() }
    }

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            currentPosition = player.currentPosition()
            delay(500)
        }
    }

    val minutes = track.duration / 60
    val seconds = track.duration % 60
    val progress = if (player.duration() > 0) currentPosition.toFloat() / player.duration().toFloat() else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Track Cover
        AsyncImage(
            model = track.coverUrl,
            contentDescription = track.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Title and Genre
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = track.title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = track.genre,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Progress Bar
        Slider(
            value = progress,
            onValueChange = {
                val newPos = (it * player.duration()).toLong()
                player.seekTo(newPos)
                currentPosition = newPos
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = Color.LightGray.copy(alpha = 0.3f)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "%d:%02d".format(currentPosition / 60000, (currentPosition / 1000) % 60),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "%d:%02d".format(minutes, seconds),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showPlaylistDialog = true }) {
                Icon(
                    imageVector = Icons.Default.PlaylistAdd,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(onClick = { player.previous() }) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            // Play/Pause Button
            FilledIconButton(
                onClick = {
                    if (isPlaying) {
                        player.pause()
                        isPlaying = false
                    } else {
                        player.play(BASE_URL + track.audioUrl.removePrefix("/"))
                        isPlaying = true
                    }
                },
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }

            IconButton(onClick = { player.next() }) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(onClick = { viewModel.toggleFavorite() }) {
                Icon(
                    imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = if (state.isFavorite) Color.Red else MaterialTheme.colorScheme.onBackground
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}
