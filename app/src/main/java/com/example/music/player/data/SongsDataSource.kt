package com.example.music.player.data

import com.example.music.player.model.entity.Song
import io.reactivex.Single

interface SongsDataSource {
    fun getSongs(): Single<List<Song>>
}