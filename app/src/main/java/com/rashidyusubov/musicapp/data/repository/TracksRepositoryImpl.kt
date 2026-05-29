package com.rashidyusubov.musicapp.data.repository

import com.rashidyusubov.musicapp.data.mapper.toDomain
import com.rashidyusubov.musicapp.data.remote.api.TracksApi
import com.rashidyusubov.musicapp.data.remote.auth.FirebaseTokenProvider
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.TracksRepository
import javax.inject.Inject

class TracksRepositoryImpl @Inject constructor(
    private val api: TracksApi,
    private val tokenProvider: FirebaseTokenProvider) : TracksRepository {

    override suspend fun searchTracks(query: String): List<Track> {

        return api.searchTracks(query).map { it.toDomain() }
    }

    override suspend fun getAllTracks(): List<Track> {
        return api.getAllTracks().map { it.toDomain() }
    }

    override suspend fun getTrackById(id: Int): Track {

        return api.getTrackById(id).toDomain()
    }

    override suspend fun getFavorites(): List<Track> {

        val token =
            tokenProvider.getToken()
                ?: return emptyList()

        return api
            .getFavorites(token)
            .map { it.toDomain() }
    }

    override suspend fun addToFavorites(trackId: Int) {

        val token = tokenProvider.getToken() ?: return

        api.addToFavorites(trackId = trackId, token = token)
    }

    override suspend fun removeFromFavorites(trackId: Int) {

        val token = tokenProvider.getToken() ?: return

        api.removeFromFavorites(trackId = trackId, token = token)
    }
}