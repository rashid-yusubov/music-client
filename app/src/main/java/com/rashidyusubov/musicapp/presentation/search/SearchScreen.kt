package com.rashidyusubov.musicapp.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.focus.onFocusChanged
import androidx.hilt.navigation.compose.hiltViewModel
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    var searchFocused by remember { mutableStateOf(false)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = state.query,

            onValueChange = {
                viewModel.updateQuery(it)
            },

            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {

                    searchFocused = it.isFocused
                },

            placeholder = {
                Text(text = "Поиск треков")
            },

            trailingIcon = {

                if (state.query.isNotEmpty()) {

                    IconButton(
                        onClick = {

                            viewModel.clearQuery()

                            keyboardController?.hide()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "Очистить"
                        )
                    }
                }
            },

            singleLine = true
        )

        Button(
            onClick = {

                keyboardController?.hide()

                viewModel.search()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "Поиск")
        }

        if (state.isLoading) {

            CircularProgressIndicator()
        }

        state.error?.let {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Ошибка подключения"
                )

                Text(
                    text = "Не удалось получить данные с сервера"
                )

                Button(
                    onClick = {
                        viewModel.retry()
                    }
                ) {

                    Text(
                        text = "Обновить"
                    )
                }
            }
        }

        if (
            state.hasSearched &&
            state.tracks.isEmpty() &&
            !state.isLoading &&
            state.error == null
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Ничего не найдено"
                )

                Text(
                    text = "Попробуйте изменить поисковый запрос"
                )
            }
        }

        if (
            searchFocused &&
            state.history.isNotEmpty()
        ) {

            Text(
                text = "История поиска"
            )

            state.history.forEach { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            viewModel.searchFromHistory(
                                item.title
                            )

                            searchFocused = false

                            keyboardController?.hide()
                        }
                        .padding(12.dp)
                ) {

                    Text(
                        text = item.title
                    )
                }
            }

            Button(
                onClick = {

                    viewModel.clearHistory()
                }
            ) {

                Text(
                    text = "Очистить историю"
                )
            }
        }

        LazyColumn {

            items(state.tracks) { track ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.saveTrackToHistory(track)
                            searchFocused = false
                            keyboardController?.hide()
                        }
                        .padding(16.dp)
                ) {

                    Text(
                        text = track.title
                    )
                }
            }
        }
    }
}