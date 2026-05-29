package com.rashidyusubov.musicapp.presentation.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rashidyusubov.musicapp.presentation.components.ArtistItem
import com.rashidyusubov.musicapp.presentation.components.TrackItem

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LazyColumn {

        item {

            Text(
                text = "Треки",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                modifier =
                    androidx.compose.ui.Modifier
                        .padding(16.dp)
            )
        }

        items(state.tracks) { track ->

            TrackItem(
                track = track,
                onClick = {

                    navController.navigate(
                        "track/${track.id}"
                    )
                }
            )
        }

        item {

            Text(
                text = "Артисты",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                modifier =
                    androidx.compose.ui.Modifier
                        .padding(16.dp)
            )
        }

        items(state.artists) { artist ->

            ArtistItem(
                artist = artist,
                onClick = {

                }
            )
        }
    }
}