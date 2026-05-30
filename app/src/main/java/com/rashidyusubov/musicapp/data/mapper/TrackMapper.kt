package com.rashidyusubov.musicapp.data.mapper

import com.rashidyusubov.musicapp.data.remote.dto.TrackDto
import com.rashidyusubov.musicapp.domain.model.Track

fun TrackDto.toDomain() = Track(
    id = id,
    title = title,
    artistId = artistId,
    artistName = artistName,
    albumId = albumId,
    duration = duration,
    genre = genre,
    audioUrl = audioUrl,
    coverUrl = coverUrl
)