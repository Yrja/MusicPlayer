package com.example.music.player.view.presenter.songs

import android.content.Context
import com.example.music.player.model.entity.Song
import com.example.music.player.view.presenter.BasePresenter

interface SongsPresenter :
    BasePresenter<SongsView> {
    fun getSongs()
    fun playSong(song: Song, context: Context)
    fun pauseOrPlayCurrentSong()
    fun destroy()
}