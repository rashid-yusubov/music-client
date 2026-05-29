package com.rashidyusubov.musicapp.data.remote.api

import com.rashidyusubov.musicapp.data.remote.dto.TrackDto

interface TracksApi {

    suspend fun searchTracks(query: String): List<TrackDto>

    suspend fun getAllTracks(): List<TrackDto>

    suspend fun getTrackById(id: Int): TrackDto

    suspend fun getFavorites(token: String): List<TrackDto>

    suspend fun addToFavorites(trackId: Int, token: String)

    suspend fun removeFromFavorites(trackId: Int, token: String)

}