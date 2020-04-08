package com.example.music.player.view.presenter

import com.example.music.player.model.entity.Song

interface SongsView :BaseView{
    fun displaySongs(songs:List<Song>)
}