package com.example.music.player.view.presenter

interface BaseView {
    fun showLoading()

    fun hideLoading()

    fun showError(error: Throwable?)
}