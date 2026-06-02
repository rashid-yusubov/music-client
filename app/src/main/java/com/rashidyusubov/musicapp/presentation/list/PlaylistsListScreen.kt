package com.rashidyusubov.musicapp.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.components.LoadingContent
import com.rashidyusubov.musicapp.presentation.playlist.PlaylistsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsListScreen(
    onBackClick: () -> Unit,
    onPlaylistClick: (Int) -> Unit,
    viewModel: PlaylistsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }
    var playlistTitle by remember { mutableStateOf("") }
    var playlistDescription by remember { mutableStateOf("") }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.padding(24.dp),
            content = {
                Surface(
                    shape = RoundedCornerShape(28.dp),
                    tonalElevation = 6.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Создать плейлист",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        OutlinedTextField(
                            value = playlistTitle,
                            onValueChange = { playlistTitle = it },
                            label = { Text("Название") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = playlistDescription,
                            onValueChange = { playlistDescription = it },
                            label = { Text("Описание") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { showCreateDialog = false }) {
                                Text("Отмена")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = {
                                    if (playlistTitle.isNotBlank()) {
                                        viewModel.createPlaylist(
                                            title = playlistTitle,
                                            description = playlistDescription.takeIf { it.isNotBlank() }
                                        )
                                        showCreateDialog = false
                                        playlistTitle = ""
                                        playlistDescription = ""
                                    }
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Создать")
                            }
                        }
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Плейлисты", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { showCreateDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Создать плейлист")
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
            if (state.isLoading) {
                LoadingContent()
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.playlists) { playlist ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPlaylistClick(playlist.id) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlaylistPlay,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = playlist.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            playlist.description?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}
