package com.rashidyusubov.musicapp.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.rashidyusubov.musicapp.presentation.components.AlbumItem
import com.rashidyusubov.musicapp.presentation.components.LoadingContent
import com.rashidyusubov.musicapp.presentation.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsListScreen(
    onBackClick: () -> Unit,
    onAlbumClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Все альбомы", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
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
                items(state.albums) { album ->
                    AlbumItem(
                        album = album,
                        onClick = { onAlbumClick(album.id) }
                    )
                }
            }
        }
    }
}
