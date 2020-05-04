package com.example.music.player.view.songs

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.music.player.model.entity.Song
import com.example.music.player.model.entity.MediaPlayerState
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class SongManager {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var songIsPaused: Boolean = false
    private val source: Subject<MediaPlayerState> = BehaviorSubject.create()

    init {
        mediaPlayer.apply {
            setAudioAttributes(AudioAttributes.Builder().build())
        }
    }

    fun playSong(song: Song, context: Context) {
        stopSong()
        mediaPlayer.apply {
            setDataSource(context, song.songContentUri)
            prepare()
            start()
        }
        source.onNext(MediaPlayerState.PLAY)
    }

    fun getMediaPlayerChanges(): Observable<MediaPlayerState> {
        return source
    }

    fun playOrPauseSong() {
        if (mediaPlayer.isPlaying) {
            source.onNext(MediaPlayerState.PAUSE)
            pauseSong()
        } else {
            source.onNext(MediaPlayerState.PLAY)
            restartPausedSong()
        }
    }

    private fun restartPausedSong() {
        mediaPlayer.start()
    }

    private fun pauseSong() {
        mediaPlayer.pause()
        songIsPaused = true
    }

    private fun stopSong() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    fun releaseMediaPlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}