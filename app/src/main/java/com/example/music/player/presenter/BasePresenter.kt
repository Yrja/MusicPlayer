package com.example.music.player.presenter

abstract class BasePresenter<V : BaseView> {
    abstract var view: V?

    fun attachView(view: V){
        this.view = view

    }

    fun detachView(){
        view = null
    }
}