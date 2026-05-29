package com.rashidyusubov.musicapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    LazyColumn {

        items(state.tracks) { track ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                        navController.navigate(
                            "track/${track.id}"
                        )
                    }
                    .padding(16.dp)
            ) {

                Text(
                    text = track.title
                )

                Text(
                    text = track.genre
                )
            }
        }
    }
}