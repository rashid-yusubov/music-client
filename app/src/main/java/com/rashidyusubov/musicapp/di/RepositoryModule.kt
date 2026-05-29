package com.rashidyusubov.musicapp.di

import com.rashidyusubov.musicapp.data.remote.api.ArtistsApi
import com.rashidyusubov.musicapp.data.remote.api.ArtistsApiImpl
import com.rashidyusubov.musicapp.data.remote.api.TracksApi
import com.rashidyusubov.musicapp.data.remote.api.TracksApiImpl
import com.rashidyusubov.musicapp.data.repository.ArtistsRepositoryImpl
import com.rashidyusubov.musicapp.data.repository.SearchHistoryRepositoryImpl
import com.rashidyusubov.musicapp.data.repository.TracksRepositoryImpl
import com.rashidyusubov.musicapp.domain.repository.ArtistsRepository
import com.rashidyusubov.musicapp.domain.repository.SearchHistoryRepository
import com.rashidyusubov.musicapp.domain.repository.TracksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTracksRepository(repository: TracksRepositoryImpl): TracksRepository

    @Binds
    abstract fun bindTracksApi(api: TracksApiImpl): TracksApi

    @Binds
    abstract fun bindSearchHistoryRepository(repository: SearchHistoryRepositoryImpl): SearchHistoryRepository

    @Binds
    abstract fun bindArtistsRepository(repository: ArtistsRepositoryImpl): ArtistsRepository

    @Binds
    abstract fun bindArtistsApi(api: ArtistsApiImpl): ArtistsApi
}