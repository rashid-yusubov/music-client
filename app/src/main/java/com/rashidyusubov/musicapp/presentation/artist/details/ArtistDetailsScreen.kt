package com.rashidyusubov.musicapp.presentation.artist.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.presentation.components.AlbumItem
import com.rashidyusubov.musicapp.presentation.components.TrackActionsBottomSheet
import com.rashidyusubov.musicapp.presentation.components.TrackItem
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel
import com.rashidyusubov.musicapp.presentation.track.TrackActionsViewModel

@Composable
fun ArtistDetailsScreen(
    artistId: Int,
    onTrackClick: (Int) -> Unit,
    onAlbumClick: (Int) -> Unit,
    viewModel: ArtistDetailsViewModel = hiltViewModel(),
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

    LaunchedEffect(artistId) {

        viewModel.loadArtist(artistId)
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

    val artist = state.artist ?: return

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {

            if (!artist.avatarUrl.isNullOrBlank()) {

                AsyncImage(
                    model = artist.avatarUrl,
                    contentDescription = artist.name,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            Text(
                text = artist.name,

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                modifier =
                    Modifier.padding(16.dp)
            )

            artist.description?.let {

                Text(
                    text = it,

                    modifier =
                        Modifier.padding(
                            horizontal = 16.dp
                        )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        item {

            Text(
                text = "Альбомы",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                modifier =
                    Modifier.padding(16.dp)
            )
        }

        items(state.albums) { album ->

            AlbumItem(
                album = album,
                onClick = {

                    onAlbumClick(
                        album.id
                    )
                }
            )
        }

        item {

            Text(
                text = "Треки",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                modifier =
                    Modifier.padding(16.dp)
            )
        }

        items(state.tracks) { track ->

            TrackItem(
                track = track,
                onClick = {

                    playerViewModel.playTracks(state.tracks, state.tracks.indexOf(track))
                },
                onMoreClick = {
                    selectedTrackForActions = track
                }
            )
        }
    }
}
