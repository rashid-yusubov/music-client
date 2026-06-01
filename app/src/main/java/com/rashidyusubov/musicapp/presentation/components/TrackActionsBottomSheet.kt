package com.rashidyusubov.musicapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rashidyusubov.musicapp.domain.model.Playlist
import com.rashidyusubov.musicapp.domain.model.Track

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackActionsBottomSheet(
    track: Track,
    playlists: List<Playlist>,
    isFavorite: Boolean,
    isInPlaylist: Boolean = false,
    initialShowPlaylists: Boolean = false,
    onDismissRequest: () -> Unit,
    onFavoriteClick: (Track) -> Unit,
    onAddToPlaylistClick: (Track, Int) -> Unit,
    onRemoveFromPlaylistClick: (Track) -> Unit = {}
) {
    var showPlaylistSelection by remember { mutableStateOf(initialShowPlaylists) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            if (!showPlaylistSelection) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                Text(
                    text = track.artistName ?: "Исполнитель",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                ListItem(
                    headlineContent = { Text(if (isFavorite) "Удалить из избранного" else "В избранное") },
                    leadingContent = {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    modifier = Modifier.clickable {
                        onFavoriteClick(track)
                        onDismissRequest()
                    }
                )

                ListItem(
                    headlineContent = { Text("Добавить в плейлист") },
                    leadingContent = { Icon(Icons.Default.PlaylistAdd, contentDescription = null) },
                    modifier = Modifier.clickable {
                        showPlaylistSelection = true
                    }
                )

                if (isInPlaylist) {
                    ListItem(
                        headlineContent = { Text("Удалить из плейлиста", color = MaterialTheme.colorScheme.error) },
                        leadingContent = { Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                        modifier = Modifier.clickable {
                            onRemoveFromPlaylistClick(track)
                            onDismissRequest()
                        }
                    )
                }
            } else {
                Text(
                    text = "Выберите плейлист",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(playlists) { playlist ->
                        ListItem(
                            headlineContent = { Text(playlist.title) },
                            leadingContent = { Icon(Icons.Default.PlaylistPlay, contentDescription = null) },
                            modifier = Modifier.clickable {
                                onAddToPlaylistClick(track, playlist.id)
                                onDismissRequest()
                            }
                        )
                    }
                    if (playlists.isEmpty()) {
                        item {
                            Text(
                                text = "Нет созданных плейлистов",
                                modifier = Modifier.padding(24.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
