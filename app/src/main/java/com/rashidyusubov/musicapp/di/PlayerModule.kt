package com.rashidyusubov.musicapp.di

import android.content.Context
import com.rashidyusubov.musicapp.presentation.player.MusicController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    @Singleton
    fun provideMusicController(@ApplicationContext context: Context): MusicController {
        return MusicController(context)
    }
}
