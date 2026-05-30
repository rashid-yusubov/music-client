package com.rashidyusubov.musicapp.presentation.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.components.EmptyContent
import com.rashidyusubov.musicapp.presentation.components.ErrorContent
import com.rashidyusubov.musicapp.presentation.components.LoadingContent
import com.rashidyusubov.musicapp.presentation.components.PlaylistItem
import com.rashidyusubov.musicapp.presentation.components.TrackItem
import com.rashidyusubov.musicapp.presentation.playlist.PlaylistsViewModel

@Composable
fun LibraryScreen(
    onTrackClick: (Int) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val playlistsViewModel: PlaylistsViewModel =
        hiltViewModel()

    val playlistsState by playlistsViewModel.state.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var playlistTitle by remember { mutableStateOf("") }
    var playlistDescription by remember { mutableStateOf("") }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Создать плейлист") },
            text = {
                Column {
                    TextField(
                        value = playlistTitle,
                        onValueChange = { playlistTitle = it },
                        label = { Text("Название") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = playlistDescription,
                        onValueChange = { playlistDescription = it },
                        label = { Text("Описание") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (playlistTitle.isNotBlank()) {
                            playlistsViewModel.createPlaylist(
                                title = playlistTitle,
                                description = playlistDescription.takeIf { it.isNotBlank() }
                            )
                            showCreateDialog = false
                            playlistTitle = ""
                            playlistDescription = ""
                        }
                    }
                ) {
                    Text("Создать")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    LaunchedEffect(Unit) {

        viewModel.loadFavorites()
    }

    if (state.isLoading) {

        LoadingContent()

        return
    }

    state.error?.let {

        ErrorContent(
            message = "Ошибка загрузки избранного",

            onRetry = {

                viewModel.loadFavorites()
            }
        )

        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),

        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        item {

            Text(
                text = "Мои плейлисты",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                modifier =
                    Modifier.padding(16.dp)
            )
        }

        item {

            TextButton(
                onClick = {

                    showCreateDialog = true
                }
            ) {

                Text("➕ Создать плейлист")
            }
        }

        items(playlistsState.playlists) { playlist ->

            PlaylistItem(

                playlist = playlist,

                onClick = {

                },

                onDelete = {

                    playlistsViewModel.deletePlaylist(
                        playlist.id
                    )
                }
            )
        }

        item {

            Text(
                text = "Избранное",

                style =
                    MaterialTheme
                        .typography
                        .headlineSmall,

                modifier =
                    Modifier.padding(16.dp)
            )
        }

        if (state.tracks.isEmpty()) {

            item {

                EmptyContent(
                    message = "Избранных треков пока нет"
                )
            }

        } else {

            items(state.tracks) { track ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        TrackItem(
                            track = track,
                            onClick = {

                                onTrackClick(track.id)
                            }
                        )
                    }

                    TextButton(
                        onClick = {

                            viewModel.removeFromFavorites(
                                track.id
                            )
                        }
                    ) {

                        Text("💔")
                    }
                }
            }
        }
    }
}