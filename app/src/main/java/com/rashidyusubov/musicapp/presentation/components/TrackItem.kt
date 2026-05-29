package com.rashidyusubov.musicapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rashidyusubov.musicapp.domain.model.Track

@Composable
fun TrackItem(
    track: Track,
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

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.size(72.dp),
                contentAlignment = Alignment.Center
            ) {

                if (!track.coverUrl.isNullOrBlank()) {

                    AsyncImage(
                        model = track.coverUrl,
                        contentDescription = track.title,

                        modifier = Modifier.fillMaxSize(),

                        contentScale = ContentScale.Crop
                    )

                } else {

                    Icon(
                        imageVector = Icons.Filled.MusicNote,
                        contentDescription = null,

                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = track.title,

                    style =
                        MaterialTheme
                            .typography
                            .titleMedium
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = track.genre,

                    style =
                        MaterialTheme
                            .typography
                            .bodyMedium
                )
            }
        }
    }
}