package com.rashidyusubov.musicapp.presentation.playlist.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.presentation.components.EmptyContent
import com.rashidyusubov.musicapp.presentation.components.ErrorContent
import com.rashidyusubov.musicapp.presentation.components.TrackActionsBottomSheet
import com.rashidyusubov.musicapp.presentation.components.TrackItem
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel
import com.rashidyusubov.musicapp.presentation.track.TrackActionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistDetailsScreen(
    playlistId: Int,
    playlistTitle: String,
    onBackClick: () -> Unit,
    onTrackClick: (Int) -> Unit,
    viewModel: PlaylistDetailsViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
    trackActionsViewModel: TrackActionsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val playlists by trackActionsViewModel.playlists.collectAsState()
    
    var selectedTrackForActions by remember { mutableStateOf<Track?>(null) }

    if (selectedTrackForActions != null) {
        TrackActionsBottomSheet(
            track = selectedTrackForActions!!,
            playlists = playlists,
            isFavorite = trackActionsViewModel.isFavorite(selectedTrackForActions!!.id),
            isInPlaylist = true,
            onDismissRequest = { selectedTrackForActions = null },
            onFavoriteClick = { trackActionsViewModel.toggleFavorite(it) },
            onAddToPlaylistClick = { track, pid ->
                trackActionsViewModel.addTrackToPlaylist(track.id, pid)
            },
            onRemoveFromPlaylistClick = { track ->
                viewModel.removeTrack(playlistId, track.id)
            }
        )
    }

    LaunchedEffect(playlistId) {
        viewModel.loadTracks(playlistId)
    }
// ...

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) {
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(playlistTitle, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.deletePlaylist(playlistId) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Удалить плейлист")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.error != null -> {
                    ErrorContent(
                        message = state.error ?: "Ошибка загрузки",
                        onRetry = { viewModel.loadTracks(playlistId) }
                    )
                }
                state.tracks.isEmpty() -> {
                    EmptyContent(message = "В этом плейлисте пока нет треков")
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.tracks) { track ->
                            TrackItem(
                                track = track,
                                onClick = {
                                    playerViewModel.playTracks(
                                        state.tracks,
                                        state.tracks.indexOf(track)
                                    )
                                },
                                onMoreClick = {
                                    selectedTrackForActions = track
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
