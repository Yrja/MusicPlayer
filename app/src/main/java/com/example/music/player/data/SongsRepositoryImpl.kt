package com.example.music.player.data

import com.example.music.player.model.SongsRepository
import com.example.music.player.model.entity.Song
import io.reactivex.Single

class SongsRepositoryImpl(private val contentResolver: SongsDataSource) : SongsRepository {
    override fun getSongs(): Single<List<Song>> {
        return contentResolver.getSongs()
    }

    override fun getSongDetail() {
        TODO("Not yet implemented")
    }
}