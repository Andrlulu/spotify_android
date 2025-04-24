package com.laioffer.spotify.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.laioffer.spotify.datamodel.Album
import com.laioffer.spotify.datamodel.Song
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    // exoPlayer is created first as the dependency of viewModel
    private val exoPlayer: ExoPlayer
) : ViewModel(), Player.Listener {
    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow() // ui state of the song
    init {
        exoPlayer.addListener(this) // this is the viewModel. viewModel registered as a player in the exoPlayer
        viewModelScope.launch {
            // create a coroutine flow, data flow,, similar to the Flow<Boolean> in database
            flow {
                while (true) {
                    if (exoPlayer.isPlaying) {
                        emit(exoPlayer.currentPosition to exoPlayer.duration)
                    }
                    delay(1000)
                }
            }.collect {
                // constantly update the current song position and update the ui state to
                _uiState.value = uiState.value.copy(currentMs = it.first, durationMs = it.second)
                Log.d("SpotifyPlayer", "CurrentMs: ${it.first}, DurationMs: ${it.second}")
            }
        }
    }
    // load album and song
    fun load(song: Song, album: Album) {
        _uiState.value = PlayerUiState(album = album, song = song, isPlaying = false)
        val mediaItem = MediaItem.Builder().setUri(song.src).build()
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    //play the song
    fun play() {
        exoPlayer.play()
    }

    //pause the song
    fun pause() {
        exoPlayer.pause()
    }
    // if want to go to next song, use load() + play()

    override fun onCleared() {
        // when viewModel is destroyed, remove the listener, to prevent circular dependency, which could cause memory leak
        // playerViewModel -> exoPlayer -> playerViewModel
        // viewModel must be destroyed before the exoPlayer is destroyed
        exoPlayer.removeListener(this) // this is the viewModel. viewModel registered as a player in the exoPlayer
        super.onCleared()
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Log.d("SpotifyPlayer", isPlaying.toString())
        _uiState.value = _uiState.value.copy(
            isPlaying = isPlaying
        )
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Log.d("spotify", error.toString())
    }
}

data class PlayerUiState(
    val album: Album? = null,
    val song: Song? = null,
    val isPlaying: Boolean = false,
    val currentMs: Long = 0,
    val durationMs: Long = 0,
)