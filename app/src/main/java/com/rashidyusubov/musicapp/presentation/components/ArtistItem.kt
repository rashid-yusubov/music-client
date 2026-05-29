package com.rashidyusubov.musicapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.domain.model.Artist

@Composable
fun ArtistItem(
    artist: Artist,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            if (!artist.avatarUrl.isNullOrBlank()) {

                AsyncImage(
                    model = artist.avatarUrl,
                    contentDescription = artist.name,

                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )

            } else {

                Icon(
                    imageVector =
                        Icons.Default.Person,

                    contentDescription = null,

                    modifier =
                        Modifier.size(64.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Text(
                text = artist.name,

                style =
                    MaterialTheme
                        .typography
                        .titleMedium
            )
        }
    }
}