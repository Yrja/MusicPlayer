package com.example.music.player.view.presenter.songs

import com.example.music.player.model.entity.Song
import com.example.music.player.view.presenter.BasePresenter

interface SongsPresenter :
    BasePresenter<SongsView> {
    fun getSongs()
    fun playSong(song: Song)
    fun stopPlayingSong()
    fun isPlaying():Boolean
    fun pauseSong()
    fun restartPausedSong()
    fun releaseMediaPlayer()
}