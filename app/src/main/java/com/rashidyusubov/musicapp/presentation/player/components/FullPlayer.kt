package com.rashidyusubov.musicapp.presentation.player.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.presentation.components.TrackActionsBottomSheet
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel
import com.rashidyusubov.musicapp.presentation.playlist.PlaylistsViewModel
import com.rashidyusubov.musicapp.presentation.track.TrackActionsViewModel
import com.rashidyusubov.musicapp.presentation.track.TrackDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullPlayer(
    track: Track,
    playerViewModel: PlayerViewModel,
    onMinimize: () -> Unit,
    trackDetailsViewModel: TrackDetailsViewModel = hiltViewModel(),
    playlistsViewModel: PlaylistsViewModel = hiltViewModel(),
    trackActionsViewModel: TrackActionsViewModel = hiltViewModel()
) {
    val isPlaying by playerViewModel.isPlaying.collectAsState()
    val progress by playerViewModel.currentPosition.collectAsState()
    val duration by playerViewModel.duration.collectAsState()
    
    val trackState by trackDetailsViewModel.state.collectAsState()
    val playlists by trackActionsViewModel.playlists.collectAsState()
    var showTrackActions by remember { mutableStateOf(false) }

    LaunchedEffect(track.id) {
        trackDetailsViewModel.loadTrack(track.id)
    }

    if (showTrackActions) {
        TrackActionsBottomSheet(
            track = track,
            playlists = playlists,
            isFavorite = trackActionsViewModel.isFavorite(track.id),
            initialShowPlaylists = true,
            onDismissRequest = { showTrackActions = false },
            onFavoriteClick = { 
                trackActionsViewModel.toggleFavorite(it)
                trackDetailsViewModel.loadTrack(track.id) // Refresh local state
            },
            onAddToPlaylistClick = { t, pid ->
                trackActionsViewModel.addTrackToPlaylist(t.id, pid)
            }
        )
    }
    
    val sliderValue = if (duration > 0) progress.toFloat() / duration.toFloat() else 0f
// ...

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onMinimize,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Minimize", modifier = Modifier.size(32.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        AsyncImage(
            model = track.coverUrl,
            contentDescription = track.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = track.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Text(
            text = track.artistName ?: "Исполнитель",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))

        Slider(
            value = sliderValue,
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
                text = formatTime(progress),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = formatTime(duration),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showTrackActions = true }) {
                Icon(
                    imageVector = Icons.Default.PlaylistAdd,
                    contentDescription = "Add to Playlist",
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = { playerViewModel.skipPrevious() }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = null, modifier = Modifier.size(48.dp))
            }

            FilledIconButton(
                onClick = { playerViewModel.togglePlayPause() },
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(onClick = { playerViewModel.skipNext() }) {
                Icon(Icons.Default.SkipNext, contentDescription = null, modifier = Modifier.size(48.dp))
            }

            IconButton(onClick = { trackDetailsViewModel.toggleFavorite() }) {
                Icon(
                    imageVector = if (trackState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(32.dp),
                    tint = if (trackState.isFavorite) Color.Red else LocalContentColor.current
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
