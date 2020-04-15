package com.example.music.player.view.presenter

import io.reactivex.disposables.CompositeDisposable

interface BasePresenter<V : BaseView> {
    var view: V?
    var compositeDisposable: CompositeDisposable

    fun attachView(view: V) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    fun detachView() {
        view = null
        compositeDisposable.clear()
    }
}