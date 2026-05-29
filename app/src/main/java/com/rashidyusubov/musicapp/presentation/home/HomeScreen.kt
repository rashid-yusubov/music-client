package com.rashidyusubov.musicapp.presentation.home

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rashidyusubov.musicapp.presentation.components.TrackItem

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    LazyColumn {

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
    }
}