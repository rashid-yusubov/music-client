package com.rashidyusubov.musicapp.data.mapper

import com.rashidyusubov.musicapp.data.remote.dto.AlbumDto
import com.rashidyusubov.musicapp.domain.model.Album

fun AlbumDto.toDomain(): Album {

    return Album(
        id = id,
        title = title,
        artistId = artistId,
        coverUrl = coverUrl,
        releaseYear = releaseYear
    )
}