package com.rashidyusubov.musicapp.presentation.album.details

import androidx.compose.foundation.clickable
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
import com.rashidyusubov.musicapp.presentation.components.TrackItem

@Composable
fun AlbumDetailsScreen(
    albumId: Int,
    onTrackClick: (Int) -> Unit,
    onArtistClick: (Int) -> Unit,
    viewModel: AlbumDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(albumId) {

        viewModel.loadAlbum(albumId)
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

    val album = state.album ?: return

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {

            if (!album.coverUrl.isNullOrBlank()) {

                AsyncImage(
                    model = album.coverUrl,
                    contentDescription = album.title,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

            Text(
                text = album.title,

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                modifier =
                    Modifier.padding(16.dp)
            )

            state.artist?.let { artist ->

                Text(
                    text = artist.name,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium,

                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {

                                onArtistClick(
                                    artist.id
                                )
                            }
                )
            }

            Text(
                text = album.releaseYear.toString(),

                modifier =
                    Modifier.padding(
                        horizontal = 16.dp
                    )
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

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

                    onTrackClick(track.id)
                }
            )
        }
    }
}