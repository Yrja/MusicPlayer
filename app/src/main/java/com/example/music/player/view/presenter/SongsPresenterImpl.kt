package com.example.music.player.view.presenter

import com.example.music.player.model.songs.SongsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SongsPresenterImpl(private val songsInteractor: SongsInteractor): SongsPresenter {

    override var view: SongsView? = null
    override var compositeDisposable = CompositeDisposable()

    override fun getSongs() {
        view?.showLoading()
           val disposable =  songsInteractor.getSongs()
                .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .doFinally { view?.hideLoading() }
                .subscribe({songs ->
                    view?.displaySongs(songs)
                },{exception ->
                    view?.showError(exception)
                })
        compositeDisposable.add(disposable)
    }

}