package com.example.music.player.view.songs

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.example.music.player.model.entity.Song

class SongManager(private val context: Context) {
    private var mediaPlayer: MediaPlayer = MediaPlayer()

    fun playSong(song: Song) {
        mediaPlayer.apply {
            setAudioAttributes(AudioAttributes.Builder().build())
            setDataSource(context, song.songContentUri)
            prepare()
            start()
        }
    }

    fun restartPausedSong() {
        mediaPlayer.start()
    }

    fun pauseSong() {
        mediaPlayer.pause();
    }

    fun stopSong() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    fun releaseMediaPlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    fun isPlaying(): Boolean = mediaPlayer.isPlaying
}