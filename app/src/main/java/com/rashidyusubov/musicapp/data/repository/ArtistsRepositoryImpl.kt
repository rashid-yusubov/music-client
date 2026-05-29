package com.rashidyusubov.musicapp.data.repository

import com.rashidyusubov.musicapp.data.mapper.toDomain
import com.rashidyusubov.musicapp.data.remote.api.ArtistsApi
import com.rashidyusubov.musicapp.domain.model.Album
import com.rashidyusubov.musicapp.domain.model.Artist
import com.rashidyusubov.musicapp.domain.model.Track
import com.rashidyusubov.musicapp.domain.repository.ArtistsRepository
import javax.inject.Inject

class ArtistsRepositoryImpl @Inject constructor(
    private val api: ArtistsApi
) : ArtistsRepository {

    override suspend fun getArtists(): List<Artist> {

        return api.getArtists().map { it.toDomain() }
    }

    override suspend fun getArtistById(id: Int): Artist {

        return api.getArtistById(id).toDomain()
    }

    override suspend fun getArtistTracks(id: Int): List<Track> {

        return api.getArtistTracks(id).map { it.toDomain() }
    }

    override suspend fun getArtistAlbums(id: Int): List<Album> {

        return api.getArtistAlbums(id).map { it.toDomain() }
    }
}