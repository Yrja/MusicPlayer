package com.example.music.player.model.songs

import com.example.music.player.model.SongsRepository
import com.example.music.player.model.entity.Song
import io.reactivex.Single
import javax.inject.Inject

class SongsInteractorImpl @Inject constructor( var repository:SongsRepository): SongsInteractor {

    override fun getSongs(): Single<List<Song>> {
        return repository.getSongs()
    }
}