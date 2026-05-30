package com.rashidyusubov.musicapp.presentation.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.rashidyusubov.musicapp.core.network.BASE_URL
import com.rashidyusubov.musicapp.domain.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val musicController: MusicController
) : ViewModel() {

    val isPlaying = musicController.isPlaying
    val currentMediaItem = musicController.currentMediaItem
    val duration = musicController.duration

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition = _currentPosition.asStateFlow()

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack = _currentTrack.asStateFlow()

    private var currentTrackList: List<Track> = emptyList()

    init {
        viewModelScope.launch {
            musicController.currentMediaItem.collect { mediaItem ->
                if (mediaItem != null) {
                    val trackId = mediaItem.mediaId.toIntOrNull()
                    _currentTrack.value = currentTrackList.find { it.id == trackId }
                        ?: _currentTrack.value // Fallback to current if not in list
                } else {
                    _currentTrack.value = null
                }
            }
        }
        viewModelScope.launch {
            while (true) {
                if (musicController.isPlaying.value) {
                    _currentPosition.value = musicController.getCurrentPosition()
                }
                delay(1000)
            }
        }
    }

    fun playTrack(track: Track) {
        currentTrackList = listOf(track)
        _currentTrack.value = track
        val mediaItem = MediaItem.Builder()
            .setUri(BASE_URL + track.audioUrl.removePrefix("/"))
            .setMediaId(track.id.toString())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(track.title)
                    .setArtist(track.artistName ?: "Исполнитель")
                    .setArtworkUri(android.net.Uri.parse(track.coverUrl ?: ""))
                    .build()
            )
            .build()
        musicController.play(mediaItem)
    }

    fun playTracks(tracks: List<Track>, startIndex: Int = 0) {
        currentTrackList = tracks
        val mediaItems = tracks.map { track ->
            MediaItem.Builder()
                .setUri(BASE_URL + track.audioUrl.removePrefix("/"))
                .setMediaId(track.id.toString())
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(track.title)
                        .setArtist(track.artistName ?: "Исполнитель")
                        .setArtworkUri(android.net.Uri.parse(track.coverUrl ?: ""))
                        .build()
                )
                .build()
        }
        _currentTrack.value = tracks[startIndex]
        musicController.playList(mediaItems, startIndex)
    }

    fun togglePlayPause() {
        if (musicController.isPlaying.value) {
            musicController.pause()
        } else {
            musicController.resume()
        }
    }

    fun skipNext() {
        musicController.next()
    }

    fun skipPrevious() {
        musicController.previous()
    }

    fun seekTo(position: Long) {
        musicController.seekTo(position)
        _currentPosition.value = position
    }

    override fun onCleared() {
        super.onCleared()
        // musicController.release() // Should we release here? Usually not if global
    }
}
