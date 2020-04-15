package com.example.music.player.model

import com.example.music.player.model.entity.Song
import io.reactivex.Single

interface SongsRepository {

    fun getSongs(): Single<List<Song>>

    fun getSongDetail()
}