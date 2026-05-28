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
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel()
) {

    val state by viewModel.state.collectAsState()

    val keyboardController =
        LocalSoftwareKeyboardController.current

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

            modifier = Modifier.fillMaxWidth(),

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

                viewModel.fakeSearch()
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(text = "Поиск")
        }

        if (state.isLoading) {

            CircularProgressIndicator()
        }

        if (
            state.tracks.isEmpty() &&
            state.query.isNotBlank() &&
            !state.isLoading
        ) {

            Text(text = "Ничего не найдено")
        }

        LazyColumn {

            items(state.tracks) { track ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(16.dp)
                ) {

                    Text(text = track)
                }
            }
        }
    }
}