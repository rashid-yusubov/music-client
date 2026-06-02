package com.rashidyusubov.musicapp.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.presentation.components.LoadingContent
import com.rashidyusubov.musicapp.presentation.components.TrackActionsBottomSheet
import com.rashidyusubov.musicapp.presentation.components.TrackItem
import com.rashidyusubov.musicapp.presentation.home.HomeViewModel
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel
import com.rashidyusubov.musicapp.presentation.track.TrackActionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TracksListScreen(
    onBackClick: () -> Unit,
    onTrackClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
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
            onDismissRequest = { selectedTrackForActions = null },
            onFavoriteClick = { trackActionsViewModel.toggleFavorite(it) },
            onAddToPlaylistClick = { track, pid ->
                trackActionsViewModel.addTrackToPlaylist(track.id, pid)
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Все треки", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                windowInsets = WindowInsets(0, 0, 0, 0),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (state.isLoading) {
                LoadingContent()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { 
                                    if (state.tracks.isNotEmpty()) {
                                        playerViewModel.playTracks(state.tracks, 0)
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(vertical = 12.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Играть все")
                            }
                            
                            OutlinedButton(
                                onClick = { 
                                    if (state.tracks.isNotEmpty()) {
                                        playerViewModel.playTracks(state.tracks.shuffled(), 0)
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(vertical = 12.dp)
                            ) {
                                Icon(Icons.Default.Shuffle, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Перемешать")
                            }
                        }
                    }
                    
                    items(state.tracks) { track ->
                        TrackItem(
                            track = track,
                            onClick = { playerViewModel.playTracks(state.tracks, state.tracks.indexOf(track)) },
                            onMoreClick = { selectedTrackForActions = track }
                        )
                    }
                }
            }
        }
    }
}
