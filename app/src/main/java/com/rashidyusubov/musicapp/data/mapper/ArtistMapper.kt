package com.rashidyusubov.musicapp.data.mapper

import com.rashidyusubov.musicapp.data.remote.dto.ArtistDto
import com.rashidyusubov.musicapp.domain.model.Artist

fun ArtistDto.toDomain(): Artist {

    return Artist(
        id = id,
        name = name,
        avatarUrl = avatarUrl,
        description = description
    )
}