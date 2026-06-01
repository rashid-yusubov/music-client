package com.rashidyusubov.musicapp.presentation.track

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistPlay
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.presentation.components.TrackActionsBottomSheet
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel
import com.rashidyusubov.musicapp.presentation.playlist.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    trackId: Int,
    viewModel: TrackDetailsViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
    playlistsViewModel: PlaylistsViewModel = hiltViewModel(),
    trackActionsViewModel: TrackActionsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val playlists by trackActionsViewModel.playlists.collectAsState()
    var showTrackActions by remember { mutableStateOf(false) }

    if (showTrackActions && state.track != null) {
        TrackActionsBottomSheet(
            track = state.track!!,
            playlists = playlists,
            isFavorite = trackActionsViewModel.isFavorite(state.track!!.id),
            initialShowPlaylists = true,
            onDismissRequest = { showTrackActions = false },
            onFavoriteClick = { 
                trackActionsViewModel.toggleFavorite(it)
                viewModel.loadTrack(trackId) // Refresh local state
            },
            onAddToPlaylistClick = { track, pid ->
                trackActionsViewModel.addTrackToPlaylist(track.id, pid)
            }
        )
    }

    LaunchedEffect(trackId) {
// ...
        viewModel.loadTrack(trackId)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        return
    }

    val track = state.track ?: return
    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val currentPosition by playerViewModel.currentPosition.collectAsState()
    val duration by playerViewModel.duration.collectAsState()

    val progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f

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
                text = track.artistName ?: "Исполнитель",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Progress Bar
        Slider(
            value = progress,
            onValueChange = {
                val newPos = (it * duration).toLong()
                playerViewModel.seekTo(newPos)
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
                text = formatTime(currentPosition),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = formatTime(duration),
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
            IconButton(onClick = { showTrackActions = true }) {
                Icon(
                    imageVector = Icons.Default.PlaylistAdd,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(onClick = { playerViewModel.skipPrevious() }) {
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
                        playerViewModel.togglePlayPause()
                    } else {
                        playerViewModel.playTrack(track)
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

            IconButton(onClick = { playerViewModel.skipNext() }) {
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

private fun formatTime(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    return "%d:%02d".format(minutes, seconds)
}
