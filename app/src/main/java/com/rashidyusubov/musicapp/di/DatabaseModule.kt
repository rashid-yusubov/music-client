package com.rashidyusubov.musicapp.di

import android.content.Context
import androidx.room.Room
import com.rashidyusubov.musicapp.data.local.dao.SearchHistoryDao
import com.rashidyusubov.musicapp.data.local.database.MusicDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MusicDatabase {

        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            "music.db"
        ).build()
    }

    @Provides
    fun provideHistoryDao(
        database: MusicDatabase
    ): SearchHistoryDao {

        return database.searchHistoryDao()
    }
}