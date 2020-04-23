package com.example.music.player.view.presenter.songs

import com.example.music.player.model.entity.Song
import com.example.music.player.model.songs.SongsInteractor
import com.example.music.player.view.songs.SongManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SongsPresenterImpl(var songsInteractor: SongsInteractor, var songManager: SongManager) :
    SongsPresenter {

    override var view: SongsView? = null
    override var compositeDisposable = CompositeDisposable()

    override fun getSongs() {
        val disposable = songsInteractor.getSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribe({ songs ->
                view?.displaySongs(songs)
            }, { exception ->
                view?.showError(exception)
            })
        compositeDisposable.add(disposable)
    }

    override fun playSong(song: Song) {
        if (isPlaying()) {
            stopPlayingSong()
        }
        songManager.playSong(song)
    }

    override fun stopPlayingSong() {
        songManager.stopSong()
    }

    override fun isPlaying(): Boolean = songManager.isPlaying()

    override fun pauseSong() {
        songManager.pauseSong()
    }

    override fun restartPausedSong() {
        songManager.restartPausedSong()
    }

    override fun releaseMediaPlayer() {
        songManager.releaseMediaPlayer()
    }
}