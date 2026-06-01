package com.rashidyusubov.musicapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.components.EmptyContent
import com.rashidyusubov.musicapp.presentation.components.ErrorContent
import com.rashidyusubov.musicapp.presentation.components.LoadingContent
import com.rashidyusubov.musicapp.presentation.components.TrackItem
import com.rashidyusubov.musicapp.presentation.player.PlayerViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel
) {
    val state by viewModel.state.collectAsState()
    var searchFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Поиск",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        // Modern Search Bar
        TextField(
            value = state.query,
            onValueChange = { viewModel.updateQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .onFocusChanged { searchFocused = it.isFocused },
            placeholder = { Text("Треки, артисты или альбомы") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
            trailingIcon = {
                if (state.query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.clearQuery(); keyboardController?.hide() }) {
                        Icon(imageVector = Icons.Outlined.Clear, contentDescription = null)
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.search()
                    keyboardController?.hide()
                    searchFocused = false
                }
            )
        )

        if (state.isLoading) {
            LoadingContent()
            return@Column
        }

        state.error?.let {
            ErrorContent(message = "Ошибка поиска", onRetry = { viewModel.retry() })
            return@Column
        }

        if (state.hasSearched && state.tracks.isEmpty()) {
            EmptyContent(message = "Ничего не найдено")
            return@Column
        }

        if (searchFocused && state.history.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "История поиска",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { viewModel.clearHistory() }) {
                    Text("Очистить", color = MaterialTheme.colorScheme.primary)
                }
            }

            state.history.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.searchFromHistory(item.title)
                            searchFocused = false
                            keyboardController?.hide()
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.History, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.tracks) { track ->
                TrackItem(
                    track = track,
                    onClick = {
                        playerViewModel.playTracks(state.tracks, state.tracks.indexOf(track))
                        viewModel.saveTrackToHistory(track)
                        searchFocused = false
                        keyboardController?.hide()
                    }
                )
            }
        }
    }
}
