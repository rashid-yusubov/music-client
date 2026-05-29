package com.rashidyusubov.musicapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.domain.model.Album

@Composable
fun AlbumItem(
    album: Album,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),

        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            if (!album.coverUrl.isNullOrBlank()) {

                AsyncImage(
                    model = album.coverUrl,
                    contentDescription = album.title,

                    modifier =
                        Modifier.size(72.dp),

                    contentScale =
                        ContentScale.Crop
                )

            } else {

                Icon(
                    imageVector =
                        Icons.Default.Album,

                    contentDescription = null,

                    modifier =
                        Modifier.size(72.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = album.title,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium
                )

                Text(
                    text =
                        album.releaseYear
                            .toString()
                )
            }
        }
    }
}