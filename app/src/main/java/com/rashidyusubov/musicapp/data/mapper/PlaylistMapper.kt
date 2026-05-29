package com.rashidyusubov.musicapp.data.mapper

import com.rashidyusubov.musicapp.data.remote.dto.PlaylistDto
import com.rashidyusubov.musicapp.domain.model.Playlist

fun PlaylistDto.toDomain(): Playlist {

    return Playlist(
        id = id,
        userId = userId,
        title = title,
        description = description,
        coverUrl = coverUrl
    )
}