package com.example.music.player.view.presenter.songs

import android.content.Context
import com.example.music.player.model.entity.Song
import com.example.music.player.model.entity.MediaPlayerState
import com.example.music.player.model.songs.SongsInteractor
import com.example.music.player.view.songs.SongManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SongsPresenterImpl(
    private val songsInteractor: SongsInteractor,
    private val songManager: SongManager
) : SongsPresenter {

    override var view: SongsView? = null
    override var compositeDisposable = CompositeDisposable()

    override fun attachView(view: SongsView) {
        super.attachView(view)
        val disposable = songManager.getMediaPlayerChanges().subscribe({
            when (it!!) {
                MediaPlayerState.PLAY -> view.setPausedImage()
                MediaPlayerState.PAUSE -> view.setPlayImage()
            }
        }, {})
        compositeDisposable.add(disposable)
    }

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

    override fun playSong(song: Song, context: Context) {
        songManager.playSong(song, context)
        view?.setPausedImage()
    }

    override fun pauseOrPlayCurrentSong() {
        songManager.playOrPauseSong()
    }

    override fun destroy() {
        songManager.releaseMediaPlayer()
    }
}