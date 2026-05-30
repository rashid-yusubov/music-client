package com.rashidyusubov.musicapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rashidyusubov.musicapp.presentation.components.AlbumItem
import com.rashidyusubov.musicapp.presentation.components.ArtistItem
import com.rashidyusubov.musicapp.presentation.components.TrackItem
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 8.dp) // Space for mini player handled by Scaffold
    ) {
        item {
            SectionHeader(
                title = "Треки",
                onSeeAllClick = { navController.navigate("all_tracks") }
            )
        }
        items(state.tracks.take(5)) { track ->
            TrackItem(
                track = track,
                onClick = { playerViewModel.playTracks(state.tracks, state.tracks.indexOf(track)) }
            )
        }

        item {
            SectionHeader(
                title = "Артисты",
                onSeeAllClick = { navController.navigate("all_artists") }
            )
        }
        items(state.artists.take(5)) { artist ->
            ArtistItem(
                artist = artist,
                onClick = { navController.navigate("artist/${artist.id}") }
            )
        }

        item {
            SectionHeader(
                title = "Альбомы",
                onSeeAllClick = { navController.navigate("all_albums") }
            )
        }
        items(state.albums.take(5)) { album ->
            AlbumItem(
                album = album,
                onClick = { navController.navigate("album/${album.id}") }
            )
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        TextButton(onClick = onSeeAllClick) {
            Text(
                text = "BCE",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
