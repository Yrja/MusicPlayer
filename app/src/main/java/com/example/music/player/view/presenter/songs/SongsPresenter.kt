package com.example.music.player.view.presenter.songs

import com.example.music.player.view.presenter.BasePresenter

interface SongsPresenter :
    BasePresenter<SongsView> {
    fun getSongs()
}