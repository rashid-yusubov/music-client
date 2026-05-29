package com.rashidyusubov.musicapp.domain.repository

import com.rashidyusubov.musicapp.domain.model.Track

interface TracksRepository {

    suspend fun searchTracks(query: String): List<Track>

    suspend fun getAllTracks(): List<Track>

    suspend fun getTrackById(id: Int): Track

    suspend fun getFavorites(): List<Track>

    suspend fun addToFavorites(trackId: Int)

    suspend fun removeFromFavorites(trackId: Int)
}