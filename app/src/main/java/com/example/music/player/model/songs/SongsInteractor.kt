package com.example.music.player.model.songs

import com.example.music.player.model.entity.Song
import io.reactivex.Single

interface SongsInteractor {
    fun getSongs(): Single<List<Song>>
}