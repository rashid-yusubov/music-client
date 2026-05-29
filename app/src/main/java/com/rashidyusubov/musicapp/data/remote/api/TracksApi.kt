package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.data.remote.dto.TrackDto

interface TracksApi {

    suspend fun searchTracks(query: String): List<TrackDto>

    suspend fun getAllTracks(): List<TrackDto>

    suspend fun getTrackById(id: Int): TrackDto
}