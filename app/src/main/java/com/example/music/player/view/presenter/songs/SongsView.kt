package com.example.music.player.view.presenter.songs

import com.example.music.player.model.entity.Song
import com.example.music.player.view.presenter.BaseView

interface SongsView : BaseView {
    fun displaySongs(songs:List<Song>)
}