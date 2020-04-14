package com.example.music.player.model.songs

import com.example.music.player.model.SongsRepository
import com.example.music.player.model.entity.Song
import io.reactivex.Single

class SongsInteractorImpl(private var repository: SongsRepository) :
    SongsInteractor {

    override fun getSongs(): Single<List<Song>> {
        return repository.getSongs()
    }
}